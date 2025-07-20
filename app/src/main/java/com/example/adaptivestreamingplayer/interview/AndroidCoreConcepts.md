# Android Core Concepts

## Android Application Lifecycle

### 1. What are the core building blocks of an Android application ?

Answer: Android apps are built using several essential components provided by the Android
framework. These components work together to handle UI, background tasks, user
interactions, and data sharing.

#### Core Building Blocks of an Android Application:

#### 1. Activities

    * Represents a single screen with a user interface.
    * Acts as the entry point for user interaction.
    * Every screen in an app in usually an Activity.

#### 2. Fragments

    * A modular piece of UI that lives inside an Activity.
    * Can be reused in multiple activities.
    * Useful for responsive layouts (e.g., tablets vs phones).

#### 3. Services

    * Runs background operations without user interactions.
    * Usefull for long-running tasks like downloading files, playing music, etc.,

#### 4. Broadcast Receivers

    * Listens to system-wide or app-wide broadcast messages.
    * Usefull for responding to events like battery low, Wi-Fi connected, etc.,

#### 5. Content Providers

    * Used to share data between apps.
    * Acts as a data access interface, like a database that can be queried using URL.

#### 6. View

    * Every UI Component like Button, TextView, etc.,
    * Created in XML or code using Jetpack Compose.

#### 7. Layouts

    * Organize UI view. Example: LinearLayout, ConstraintLayout, Compose Column/Row.

#### 8. Manifest File

    * Declares all components, permissions, and metadata.
    * It's like the blueprint of your app.

### 2. What is the Intent ?

Answer: An Intent is serves as a messaging object that allows activities, services, and broadcast
receivers to communicate. Intents are typically used to start an activity, send a broadcast, or
initiate a service. They can also pass data between components, making them a fundamental part of
Android’s component-based architecture.

There are two primary types of intents in Android: explicit and implicit.

1. Explicit Intent:
    * Definition: An explicit intent specifies the exact component (activity or service) to be
      invoked by directly naming it.
    * Use Case: Explicit intents are used when you know the target component (e.g., starting
      a specific activity within your app).
    * Scenario: If you’re switching from one activity to another within the same app, you
      use an explicit intent.
    * Eg: You can use the explicit intent like the code below:
   ```kotlin
        val intent = Intent(this, TargetActivity::class.java)
        startActivity(intent)
    ```
2. Implicit Intent:
    * Definition: An implicit intent does not specify a specific component but declares a
      general action to be performed. The system resolves which component(s) can handle
      the intent based on the action, category, and data.
    * Use Case: Implicit intents are useful when you want to perform an action that other
      apps or system components can handle (e.g., opening a URL or sharing content).
    * Scenario: If you’re opening a web page in a browser or sharing content with other
      apps, you use an implicit intent. The system will decide which app to handle the
      intent.
    * Eg: You can use the implicit intent like the code below:
   ```kotlin
        val intent = Intent(Intent.ACTION_VIEW)
         intent.data = Uri.parse("https://www.example.com")
         startActivity(intent)
     ```

[Summary]:
**Explicit intents** are used for internal app navigation where the target component is known. On
the other hand, **Implicit intents** are used for actions that may be handled by external apps or
other components without directly specifying the target. This makes the Android ecosystem more
flexible and allows apps to interact seamlessly.

[Practical Questions]
Q) What is the key difference between explicit and implicit intents, and in what scenarios
would you use each?
**Explicit Intent**: An explicit intent specifies the exact component (class name) to handle the
intent. It is commonly used within the same application when you know the target activity or
service.
**Eg**: Navigating from MainActivity to DetailActivity within the same app.

```kotlin
   val intent = Intent(this, DetailActivity::class.java)
startActivity(intent)
```

**Implicit Intent**: An implicit intent does not specify a component. Instead, it declares a general
action to be performed, and the Android system resolves it by finding a suitable component (from any
app) that can handle the intent.
**Eg**: Sharing text via available apps:

```kotlin
   val intent = Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, "Hello World")
}
startActivity(Intent.createChooser(intent, "Share via"))
```

Q) How does the Android system determine which app should handle an implicit intent, and
what happens if no suitable application is found?

* The Android system queries the PackageManager for all installed apps with intent filters that
  match the action, data, and category of the implicit intent.
* If one app matches, it is launched directly.
* If multiple apps match, the system shows a chooser dialog to let the user select the app.
* If no app matches the intent, an **ActivityNotFoundException** is thrown.
* To avoid crashes when no app is found, it is good practice to first check:

```kotlin
if (intent.resolveActivity(packageManager) != null) {
    startActivity(intent)
} else {
    // Handle the case (e.g., show a message to the user)
}
```