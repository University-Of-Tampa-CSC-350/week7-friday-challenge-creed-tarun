[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/QS3u7J-v)
# FC 007
This challenge can be worked on in pairs of 2 and the focus for this challenge is on the topics we covered this week, sensors!

**_Note that all memebers must have some contribution towards the challenge. Zero or very little contribution
will result in a zero in this assignment._**

## Github Classrooms
We will be using Github classroom to do in-class Friday Challenges.
You can make as many commits and as many pushes as  needed to the main branch on your forked copy of the repo.
The notes about commits are still relevant here:
To be consistent, use the same styling for commit messages that was given in the Project I:
- PREFIX – Short description of the change
  A detailed description can be added to the commit in the long description, if needed.
  The following are the possible options for [Prefix]
- [FEAT] - For new features or major additions to the project.
  FEAT - Added button click-ability feature 
- [FIX] - For bug fixes, corrections, or revisions to the code.
  FIX - Corrected navigation bar alignment on mobile devices
- [STYLE] - For stylistic changes such as formatting, CSS modifications, or minor visual updates.
  STYLE - Updated color scheme for better contrast
- [DOCS] - For changes or additions to the documentation, including README files and comments in the code.
  DOCS - Added project description and setup instructions to README
- [SECURITY] - For changes related to improving the security of the website.
  SECURITY - Implemented input validation for contact form
- [REFACTOR] - For code refactoring that doesn’t change functionality but improves code quality or organization.
  REFACTOR - Organized attributes for button components files in Home layout.
- [TEST] - For adding tests or making changes to the testing suite.
  TEST - Added validation tests for login form input

### 5. Submitting your work
Once, you are sure that all the work is completed, go through the following steps for submission.
Push all your work onto the main branch. **Only the main branch** will be considered for grading.

## Project description

Your application will function as a simple motion-controlled system that responds to real-world device movement.
Using the device’s accelerometer, your application will detect tilt and translate that input into movement of an on-screen object.

This challenge introduces the concept of mapping physical input → digital behavior, which is commonly used in interactive systems and game development.

The goal of this challenge is to build a simple interface that:
* Reads accelerometer data from the device
* Maps motion input to object movement on screen
* Updates the UI in real time based on tilt
* Maintains smooth and responsive behavior
* Extra points for creating visually appealing or well-structured UI

Total points - 46

Task 1 - 3

Task 2 - 5

Task 3 - 10

Task 4 - 10

Task 5 - 10

Task 6 - 5

Task 7 - 3

Bonus  +2


### Task 1: Access Accelerometer
Setup and use the accelerometer to detect device motion.
Refer to this week’s slides for:
* SensorManager setup
* Registering and unregistering listeners
* Reading X, Y, Z values
### Task 2: Create the UI
Your layout must contain:
* A visual object (View or ImageView) that will move on the screen
* A container (screen or layout) that defines the movement area
Visualize a ball inside a box with no top, we will later build games using Unity, but for now a 2D setup should do.
You may design the UI as you prefer.
### Task 3: Map Motion to Movement
Use accelerometer values to control object position:
* X-axis → horizontal movement
* Y-axis → vertical movement
Movement must:
* Be continuous (not step-based)
* Feel responsive to tilt
* Be scaled appropriately (raw values are small)
### Task 4: Maintain Screen Boundaries
The object must remain within the visible screen area. Do a trail and error method to figure out 
how much room you have on the screen to play around with.
* Prevent it from moving off-screen
* Handle edge conditions properly
### Task 5: Real-Time Updates
The object position should update continuously as sensor values change.
* Movement must reflect live input
* Avoid lag or delayed updates
### Task 6: Handle Sensor Availability
Not all devices have all sensors.
* Check if the accelerometer is available
* Prevent crashes if unavailable
* Provide fallback behavior (e.g., display a message)
### Task 7: Lifecycle Management
Properly manage sensor lifecycle:
* Register listener in onResume()
* Unregister listener in onPause()

Refer to slides for implementation details.

**Expected Behavior:**
* Tilting the device moves the object
* Movement direction matches tilt direction
* Object responds smoothly to motion
* App remains stable across lifecycle changes
### Constraints
* Do not use buttons for movement
* Movement must be driven only by sensor input
* Do not hardcode positions, although you could hardcode the boundaries for now.
* App must not crash if sensor is unavailable
### Bonus Points
+2 points for:
* Smooth movement (reduced jitter)
* Sensitivity control (adjustable speed)
* Displaying X/Y values on screen
* Clean and visually structured UI

* **Note:**

Relevant implementation details for:
* Sensor setup
* Listener registration
* Lifecycle handling

are provided in this week’s lecture slides. You are expected to refer to them while completing the challenge.

