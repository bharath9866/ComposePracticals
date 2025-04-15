### AndroidManifest.xml Interview Questions

#### 1. What is the AndroidManifest.xml file and why is it important?

**Answer:** AndroidManifest.xml is a crucial configuration file that provides essential information
about your application to the Android system. It:

* Defines the app's package name and version
* Declares required permissions
* Registers all application components activities, services, etc.
* Specifies the entry point activity
* Defines hardware/software requirements
* Sets theme and icon information
* The system needs this information before it can run any of the app's code.

#### 2. What happens if you don't declare an activity in the manifest?

**Answer:** If you don't declare an activity in the manifest, the Android system won't recognize it,
and you'll receive a runtime exception ActivityNotFoundException when attempting to start that
activity. All application components activities, services, broadcast receivers, content providers
must be declared in the manifest to be accessible.

#### 3. What is the purpose of the "android:exported" attribute?

**Answer:** The `android:exported` attribute determines whether a component activity, service, etc.
can be invoked by components from other applications:

* `true`: The component can be accessed by other applications
* `false`: The component can only be accessed by components within the same application or
  applications with the same user ID
  Since Android 12, this attribute is mandatory for components with intent filters. It's a security
  feature to prevent unintended access to your app's components.

#### 4. Explain intent filters in the AndroidManifest.xml.

**Answer:** Intent filters declare what types of intents a component can respond to. They consist
of:

* **Actions**: What operation to perform e.g., MAIN, VIEW, SEND
* **Categories**: Additional information about the component e.g., LAUNCHER, BROWSABLE
* **Data**: Specifies the data type the component can handle MIME types, URI schemes
  For example, the following declares an activity as the launcher activity:

```xml

<intent-filter>
    <action android:name="android.intent.action.MAIN" />
    <category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
```

#### 5. How do you handle permissions in Android?

**Answer:** Permissions are declared in the manifest using the `<uses-permission>` tag. There are
two types:

1. **Normal permissions**: Automatically granted at installation e.g., INTERNET
2. **Dangerous permissions**: Require runtime permission from the user e.g., CAMERA, LOCATION
   For dangerous permissions on Android 6.0+, you must:

* Declare them in the manifest
* Request them at runtime using the permissions API
* Handle permission results and request rationale when needed

#### 6. What's the difference between `android:allowBackup` and how does it affect security?

**Answer:** The `android:allowBackup` attribute in the application tag determines if an app's data
can be backed up and restored:

* When `true`: User data can be backed up via ADB or cloud backup services
* When `false`: The app opts out of backup systems
  Security implications:
* Setting it to `true` default means app data might be accessible during backup/restore operations
* For apps with sensitive data, setting it to `false` prevents potential data exposure
* For additional security, you can use `android:fullBackupContent` to specify exactly what gets
  backed up

#### 7. How does the manifest merge process work with libraries?

**Answer:** When building an Android app with libraries, multiple manifest files exist app's
manifest and each library's manifest. The Android Gradle plugin performs manifest merging:

1. The app's manifest serves as the base
2. Library manifests are merged in order of dependency
3. Conflicts are resolved based on priority and merge rules
4. Elements from libraries are added to the app's manifest
5. Duplicate declarations might cause build errors if not resolved
   You can control the process with merge rules like `tools:node="remove"` or
   `tools:replace="attribute"`.

#### 8. What are the key differences between `<activity>`, `<service>`, `<receiver>`, and

`<provider>` in the manifest?

**Answer:**

* **Activity**: UI components that users interact with
* **Service**: Background components for long-running operations without UI
* **Receiver** BroadcastReceiver: Components that respond to system-wide broadcasts
* **Provider** ContentProvider: Components that manage shared app data
  Each needs different attributes:
* Activities need `android:name` and possibly intent filters
* Services need `android:name` and often `android:exported`
* Receivers need `android:name` and intent filters for specific broadcasts
* Providers need `android:name`, `android:authorities`, and `android:exported`

#### 9. What is the role of `<queries>` element introduced in Android 11?

**Answer:** The <queries> element was introduced with Android 11's package visibility changes. It
allows your app to declare which other apps it needs to interact with:

```xml

<queries>
    <package android:name="com.example.store" />
    <intent>
        <action android:name="android.intent.action.VIEW" />
        <data android:scheme="https" />
    </intent>
</queries>
```

Without this declaration, apps on Android 11+ can't discover or interact with other apps using
methods like queryIntentActivities() unless they match your queries filter or you hold the
QUERY_ALL_PACKAGES permission which is restricted.

#### 10. How does the `android:launchMode` attribute affect activity behavior?

**Answer**: `android:launchMode` controls how activities are instantiated and managed in the back
stack:

* **standard** default: Creates a new instance every time
* **singleTop**: Reuses the instance if it's already at the top of the stack
* **singleTask**: Creates a new task and places a single instance at the root
* **singleInstance**: Like singleTask but no other activities can be part of that task
  This impacts navigation flow, instance management, and intent handling behavior.

#### 11. android:launchMode Detailed Explanation?

The `android:launchMode` attribute in AndroidManifest.xml controls how activities are instantiated
and managed in the back stack. This is a critical concept for controlling application navigation
flow and preserving state.

##### Overview of Launch Modes:
Android provides four launch modes that determine:
* How new instances of activities are created
* Where these instances are placed in the task stack
* How the back stack behaves when navigating

##### The Four Launch Modes:
1. **standard (Default)**
   **Behavior:**
    * Creates a new instance of the activity every time it's requested
    * Multiple instances of the same activity can exist in the same task or across tasks
    * Each instance receives its own `onCreate()`, `onStart()`, etc. callbacks
    * Instances are stacked in order of creation
      **Example:** If you have Activity A and launch Activity B three times with standard mode, your
      stack will be:
    * [A → B → B → B]
      **Use cases:**
    * General-purpose activities that can have multiple independent instances
    * When you need separate contexts for the same UI like viewing different items
2. **singleTask**
   **Behavior:**
    * If an instance of the activity already exists at the top of the stack, that instance is reused
    * If the instance exists but is not at the top, a new instance is created
    * When reused, `onNewIntent()` is called instead of `onCreate()`
    * Preserves the existing instance state
      **Example:** Starting with stack A → B:
    * Launching B again results in A → B same instance, calls `onNewIntent()`
    * Launching A from B results in A → B → A new A instance created
      **Use cases:**
    * Search activities where repeated searches should reuse the same screen
    * Notification handling where multiple notifications should be processed in the same activity
    * Avoiding duplicate screens when rapidly tapping navigation
3. **singleTask**
   **Behavior:**
    * Only one instance of the activity can exist in the system at a time
    * When launched, if the activity exists in any task, that task is brought to the foreground
    * All activities above it in the stack are destroyed stack clearing
    * `onNewIntent()` is called if the activity already exists
    * Can specify a unique task affinity to control which task hosts the activity
      **Example:** Starting with stack A → B → C → D:
    * If B is singleTask and gets launched, the stack becomes A → B C and D are destroyed
    * If a new activity E is launched from B, the stack becomes A → B → E
      **Use cases:**
    * Home screen or main dashboard activities
    * Activities that serve as entry points to major features
    * When you need an activity to always be at a predictable state in the back stack
4. **singleInstance**
   **Behavior:**
   * Only one instance of the activity can exist in the system at a time
   * The activity is always the sole activity in its task dedicated task
   * No other activities can be added to this task
   * When the user navigates away and back, the original instance is always reused
   * Calls to `startActivityForResult()` work differently with this mode
   **Example:** If you have Task 1 A → B → C and launch D with singleInstance:
   * D gets its own task Task 2
   * If D starts activity E, E will be added to Task 1, not Task 2
   * Back navigation gets complex as the user moves between separate tasks
   **Use cases:**
   * Activities that should never have other activities in their task
   * Special-purpose activities like phone call screens or alarm screens
   * Activities that need to be accessible from anywhere in the system with consistent behavior

##### Launch Modes and Intent Flags
Launch modes can be overridden by Intent flags when starting activities:
* `FLAG_ACTIVITY_NEW_TASK` similar to singleTask
* `FLAG_ACTIVITY_SINGLE_TOP` similar to singleTop
* `FLAG_ACTIVITY_CLEAR_TOP` clears activities above the target
Intent flags take precedence over manifest launch modes when both are specified.

##### Task Affinity and Launch Modes
Task affinity set via `android:taskAffinity` defines which task an activity prefers to belong to. This becomes especially important with singleTask and singleInstance launch modes:
* An activity with singleTask launch mode and a custom task affinity will be launched in a task matching that affinity, or create a new task if none exists
* This allows you to create separate "stacks" of activities that behave differently

###### Common Pitfalls
1. **Back Stack Navigation Issues**: singleTask and singleInstance can create unexpected back stack behavior
2. **Data Loss**: Activities cleared from the stack lose their state
3. **Unexpected Results with Flags**: Combining launch modes with intent flags can produce complex behaviors
4. **Fragment Complications**: Launch modes affect how fragments behave within activities
5. **startActivityForResult Problems**: singleTask and singleInstance can break expected result delivery
