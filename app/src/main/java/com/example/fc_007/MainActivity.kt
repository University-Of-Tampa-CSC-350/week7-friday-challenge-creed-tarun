package com.example.fc_007

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Surface
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private lateinit var sensorStatusBadge: TextView
    private lateinit var statusMessageText: TextView
    private lateinit var tiltXValueText: TextView
    private lateinit var tiltYValueText: TextView
    private lateinit var tiltZValueText: TextView
    private lateinit var sensitivityValueText: TextView
    private lateinit var sensitivitySlider: Slider
    private lateinit var arena: FrameLayout
    private lateinit var ballView: View

    private var ballX = 0f
    private var ballY = 0f
    private var minBallX = 0f
    private var maxBallX = 0f
    private var minBallY = 0f
    private var maxBallY = 0f
    private var smoothedTiltX = 0f
    private var smoothedTiltY = 0f
    private var movementScale = DEFAULT_MOVEMENT_SCALE
    private var arenaReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorStatusBadge = findViewById(R.id.sensorStatusBadge)
        statusMessageText = findViewById(R.id.statusMessageText)
        tiltXValueText = findViewById(R.id.tiltXValueText)
        tiltYValueText = findViewById(R.id.tiltYValueText)
        tiltZValueText = findViewById(R.id.tiltZValueText)
        sensitivityValueText = findViewById(R.id.sensitivityValueText)
        sensitivitySlider = findViewById(R.id.sensitivitySlider)
        arena = findViewById(R.id.arena)
        ballView = findViewById(R.id.ballView)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensitivitySlider.addOnChangeListener { _, value, _ ->
            movementScale = value
            sensitivityValueText.text = getString(R.string.sensitivity_value_format, value)
        }
        sensitivityValueText.text =
            getString(R.string.sensitivity_value_format, sensitivitySlider.value)

        arena.doOnLayout {
            updateArenaBounds(centerBall = !arenaReady)
        }

        updateSensorAvailabilityUi()
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER || !arenaReady) {
            return
        }

        val (tiltX, tiltY) = remapToScreenAxes(event.values[0], event.values[1])
        val tiltZ = event.values[2]

        tiltXValueText.text = getString(R.string.readout_value_format, tiltX)
        tiltYValueText.text = getString(R.string.readout_value_format, tiltY)
        tiltZValueText.text = getString(R.string.readout_value_format, tiltZ)

        smoothedTiltX += (tiltX - smoothedTiltX) * SMOOTHING_ALPHA
        smoothedTiltY += (tiltY - smoothedTiltY) * SMOOTHING_ALPHA

        ballX = (ballX + smoothedTiltX * movementScale).coerceIn(minBallX, maxBallX)
        ballY = (ballY - smoothedTiltY * movementScale).coerceIn(minBallY, maxBallY)

        ballView.x = ballX
        ballView.y = ballY
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    private fun updateSensorAvailabilityUi() {
        if (accelerometer == null) {
            sensorStatusBadge.text = getString(R.string.sensor_status_missing)
            sensorStatusBadge.setTextColor(getColor(R.color.danger_700))
            sensorStatusBadge.setBackgroundResource(R.drawable.status_chip_unavailable)
            statusMessageText.text = getString(R.string.sensor_message_missing)
            sensitivitySlider.isEnabled = false
            tiltXValueText.text = getString(R.string.readout_placeholder)
            tiltYValueText.text = getString(R.string.readout_placeholder)
            tiltZValueText.text = getString(R.string.readout_placeholder)
            return
        }

        sensorStatusBadge.text = getString(R.string.sensor_status_ready)
        sensorStatusBadge.setTextColor(getColor(R.color.success_700))
        sensorStatusBadge.setBackgroundResource(R.drawable.status_chip_available)
        statusMessageText.text = getString(R.string.sensor_message_ready)
    }

    private fun updateArenaBounds(centerBall: Boolean) {
        minBallX = arena.paddingLeft.toFloat()
        maxBallX = (arena.width - arena.paddingRight - ballView.width).toFloat().coerceAtLeast(minBallX)
        minBallY = arena.paddingTop.toFloat()
        maxBallY = (arena.height - arena.paddingBottom - ballView.height).toFloat().coerceAtLeast(minBallY)

        if (centerBall) {
            ballX = (minBallX + maxBallX) / 2f
            ballY = (minBallY + maxBallY) / 2f
        } else {
            ballX = ballX.coerceIn(minBallX, maxBallX)
            ballY = ballY.coerceIn(minBallY, maxBallY)
        }

        ballView.x = ballX
        ballView.y = ballY
        arenaReady = true
    }

    @Suppress("DEPRECATION")
    private fun remapToScreenAxes(rawX: Float, rawY: Float): Pair<Float, Float> {
        return when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_90 -> -rawY to rawX
            Surface.ROTATION_180 -> -rawX to -rawY
            Surface.ROTATION_270 -> rawY to -rawX
            else -> rawX to rawY
        }
    }

    private companion object {
        const val DEFAULT_MOVEMENT_SCALE = 1.4f
        const val SMOOTHING_ALPHA = 0.16f
    }
}
