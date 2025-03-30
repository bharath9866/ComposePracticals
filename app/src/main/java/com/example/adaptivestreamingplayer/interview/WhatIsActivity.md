# Recreate the markdown file after execution state reset

# Define the content again

# Android Interview Questions and Answers

## 1. What is an Activity in Android?

**Answer:**  
- An Activity in Android is a crucial component that provides a screen with which users can interact.
- It acts as a container for UI elements and handles user interactions.
- Activities also serve as entry points for applications, enabling users to launch the app from the home screen or via an external source like a web link.

---

## 2. Explain the Android Activity Lifecycle.

**Answer:**  
The Activity Lifecycle defines different states an activity goes through from creation to
destruction. The main states are:

1. **onCreate()** – Called when the activity is first created. Used for initializing components and
   setting up the UI.
2. **onStart()** – Called when the activity becomes visible to the user.
3. **onResume()** – Called when the user starts interacting with the app.
4. **onPause()** – Called when another UI element (like a dialog) appears, partially covering the
   activity.
5. **onStop()** – Called when the activity is no longer visible.
6. **onRestart()** – Called when the activity is restarted after being stopped.
7. **onDestroy()** – Called when the activity is about to be destroyed, freeing resources.

---

## 3. What is the difference between onPause() and onStop()?

**Answer:**

- **onPause()**: Called when the activity loses focus but is still partially visible (e.g., a dialog
  appears).
- **onStop()**: Called when the activity is no longer visible at all (e.g., when navigating to
  another activity).

---

## 4. What happens when an activity is rotated?

**Answer:**  
When an activity is rotated, the system destroys and recreates the activity. This is because a
configuration change occurs, requiring the UI to be re-initialized. The activity lifecycle starts
again from `onDestroy()` → `onCreate()`. To handle configuration changes efficiently, developers can
use `ViewModel`, `onSaveInstanceState()`, or `configChanges` in the manifest.

---

## 5. How do you prevent activity recreation during configuration changes?

**Answer:**  
To prevent activity recreation when a configuration change occurs (like screen rotation), you can:

- Use `android:configChanges="orientation|screenSize"` in the AndroidManifest.xml.
- Store necessary data in a `ViewModel` to persist across recreation.
- Save and restore data using `onSaveInstanceState()` and `onRestoreInstanceState()`.

---

## 6. How does Jetpack Compose affect activity usage?

**Answer:**  
With Jetpack Compose, many apps now use a **single-activity architecture**. Instead of multiple
activities managing different screens, a single activity hosts different composable functions,
making navigation and UI management more efficient.

---

## 7. What is the role of onRestart() in the activity lifecycle?

**Answer:**  
The `onRestart()` method is called when an activity that was previously stopped is being restarted.
This happens when a user navigates back to an app that was minimized.

---

## 8. When is onDestroy() called, and what are its use cases?

**Answer:**  
The `onDestroy()` method is called when:

- The activity is explicitly closed using `finish()`.
- The user presses the back button.
- The system needs to free up memory and decides to kill the activity.

Use cases:

- Releasing resources like background threads or unregistering broadcast receivers.
- Closing database connections.

---