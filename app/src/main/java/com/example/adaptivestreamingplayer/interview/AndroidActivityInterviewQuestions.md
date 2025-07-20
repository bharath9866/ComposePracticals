# Android Activity Interview Questions and Answers

## Basic Concepts

### What are the core building blocks of an Android application?

**Answer:**  
Android applications are built using several essential components provided by the Android framework. These components work together to handle the user interface, background tasks, user interactions, and data sharing.

#### Core Building Blocks of an Android Application:

#### 1. **Activities**
- Represents a single screen with a user interface.
- Acts as the entry point for user interaction.
- Every screen in an app is usually an activity.

#### 2. **Fragments**
- A modular section of UI that lives inside an activity.
- Can be reused in multiple activities.
- Useful for creating responsive layouts (e.g., tablets vs. phones).

#### 3. **Services**
- Runs background operations without direct user interaction.
- Useful for long-running tasks like downloading files or playing music.

#### 4. **Broadcast Receivers**
- Listens for system-wide or app-specific broadcast messages.
- Useful for responding to events like low battery, Wi-Fi connectivity changes, etc.

#### 5. **Content Providers**
- Used to share data between different applications.
- Acts as a data access interface, typically backed by a database and accessed using URIs.

#### 6. **View**
- Represents UI components such as `Button`, `TextView`, etc.
- Created using XML layouts or programmatically using Jetpack Compose.

#### 7. **Layouts**
- Used to organize and position UI components.
- Examples: `LinearLayout`, `ConstraintLayout`, or Compose `Column`/`Row`.

#### 8. **AndroidManifest.xml**
- Declares all components, permissions, and application metadata.
- Serves as the blueprint of the application.


### What is an Android Activity?

An Activity is a fundamental component of Android applications that represents a single screen with
a user interface. It's not just a container for UI elements but a unit of your app where users
interact. Each Activity serves as an entry point for user interaction with the application.

**Practical Example:**

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize UI components
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}
```

### Why are Activities called "Activities" and not "Screens"?

Activities are called such because they represent units of user interaction rather than just visual
containers. They encompass not only what the user sees but also what the user can do, the state of
that interaction, and how the system should handle that interaction in different scenarios.

### How have Activities evolved in modern Android development?

- **Traditional approach**: One Activity per screen
- **Fragment era**: Multiple related screens bundled into one Activity using Fragments
- **Jetpack Compose era**: Single Activity architecture where the main Activity serves as the entry
  point, and all screens are managed as composable functions within that Activity

## Activity Lifecycle

### What are the main lifecycle states of an Activity?

1. **Created**: The Activity is being initialized
2. **Started**: The Activity is visible but not interactive
3. **Resumed**: The Activity is in the foreground and interactive
4. **Paused**: The Activity is partially visible but not in focus
5. **Stopped**: The Activity is not visible
6. **Destroyed**: The Activity is terminated and removed from memory

### What are the key lifecycle callback methods of an Activity?

- `onCreate()`: Called when the Activity is first created. Used for one-time initialization, UI
  setup.
- `onStart()`: Called when the Activity becomes visible to the user.
- `onResume()`: Called when the Activity starts interacting with the user.
- `onPause()`: Called when the Activity loses focus but is still partially visible.
- `onStop()`: Called when the Activity is no longer visible to the user.
- `onRestart()`: Called after the Activity has been stopped and is restarting again.
- `onDestroy()`: Called before the Activity is destroyed.

**Practical Example:**

```kotlin
class LifecycleActivity : AppCompatActivity() {
    private val TAG = "LifecycleActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        Log.d(TAG, "onCreate called")
    }
    
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
        // Start animations, resume video playback, etc.
    }
    
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
        // Save important data, pause video playback, etc.
    }
    
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
        // Release resources that aren't needed while the activity is not visible
    }
    
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
        // Clean up any resources that weren't already released
    }
}
```

### When is onPause() called?

`onPause()` is called when the Activity loses focus but might still be partially visible. This
happens when:

- A dialog appears over the Activity
- Another Activity with transparent background comes into foreground
- Multi-window mode is used and another app gets focus
- The user starts transitioning to another Activity

### What's the difference between onPause() and onStop()?

- `onPause()`: The Activity may still be visible in the background but has lost focus.
- `onStop()`: The Activity is completely hidden from the user and no longer visible.

### When would an Activity be destroyed?

An Activity is destroyed when:

- The user explicitly closes it (by pressing back button)
- The Activity calls `finish()`
- The system needs to reclaim memory
- Configuration changes occur (like screen rotation)
- The app process is killed

### Which lifecycle callbacks are not guaranteed to be called?

`onStop()` and `onDestroy()` are not guaranteed to be called in all situations, especially if the
system terminates the application process abruptly due to memory constraints.

### Where should you save important data when the user is leaving your app?

Important data should be saved in `onPause()` since it's guaranteed to be called when the user moves
away from your Activity, while `onStop()` and `onDestroy()` are not guaranteed to be called.

**Practical Example:**

```kotlin
class NoteActivity : AppCompatActivity() {
    private lateinit var noteEditText: EditText
    private val PREFS_NAME = "NotePrefs"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        
        noteEditText = findViewById(R.id.noteEditText)
        
        // Restore saved note if exists
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        noteEditText.setText(prefs.getString("note_text", ""))
    }
    
    override fun onPause() {
        super.onPause()
        
        // Save note text when activity is paused
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString("note_text", noteEditText.text.toString()).apply()
    }
}
```

## Configuration Changes

### What happens to an Activity during a configuration change like screen rotation?

During a configuration change:

1. The Activity is destroyed (going through `onPause()`, `onStop()`, and `onDestroy()`)
2. The Activity is recreated (going through `onCreate()`, `onStart()`, and `onResume()`)
3. Any state not explicitly saved will be lost

**Practical Example:**

```kotlin
class ConfigChangeActivity : AppCompatActivity() {
    private var counter = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_change)
        
        // Restore counter value if available
        savedInstanceState?.let {
            counter = it.getInt("counter", 0)
        }
        
        val counterTextView = findViewById<TextView>(R.id.counterTextView)
        val incrementButton = findViewById<Button>(R.id.incrementButton)
        
        counterTextView.text = "Counter: $counter"
        incrementButton.setOnClickListener {
            counter++
            counterTextView.text = "Counter: $counter"
        }
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        // Save the counter value before configuration change
        outState.putInt("counter", counter)
        super.onSaveInstanceState(outState)
    }
}
```

### What are common examples of configuration changes?

- Screen rotation
- Keyboard availability changes
- Language changes
- Theme changes
- Font size changes

## Activity Navigation and Communication

### How can Activities communicate with each other?

- **Intents**: Using explicit intents to launch specific Activities and pass data through extras
- **Shared ViewModel**: Activities in the same process can share a ViewModel
- **Broadcast Receivers**: For less direct communication across app components
- **Result API**: For getting results back from an Activity
- **Shared Preferences or databases**: For persistent data storage

**Practical Examples:**

1. **Using Intents:**

```kotlin
// In FirstActivity
class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        
        findViewById<Button>(R.id.navigateButton).setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("message", "Hello from FirstActivity")
                putExtra("count", 42)
            }
            startActivity(intent)
        }
    }
}

// In SecondActivity
class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        
        // Retrieve data from intent
        val message = intent.getStringExtra("message") ?: "No message"
        val count = intent.getIntExtra("count", 0)
        
        findViewById<TextView>(R.id.messageTextView).text = "$message (count: $count)"
    }
}
```

2. **Using ActivityResultLauncher (Modern Result API):**

```kotlin
// In CallerActivity
class CallerActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val returnedText = data?.getStringExtra("result") ?: "No data returned"
            resultTextView.text = returnedText
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caller)
        
        resultTextView = findViewById(R.id.resultTextView)
        
        findViewById<Button>(R.id.launchForResultButton).setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            getResult.launch(intent)
        }
    }
}

// In ResultActivity
class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        
        findViewById<Button>(R.id.sendResultButton).setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("result", "Data from ResultActivity")
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
```

3. **Using Shared ViewModel:**

```kotlin
// Shared ViewModel
class SharedViewModel : ViewModel() {
    val message = MutableLiveData<String>()
    
    fun updateMessage(newMessage: String) {
        message.value = newMessage
    }
}

// In FirstActivity
class FirstActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        
        // Get the ViewModel
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        
        findViewById<Button>(R.id.updateButton).setOnClickListener {
            sharedViewModel.updateMessage("Updated at ${System.currentTimeMillis()}")
            // Navigate to second activity
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}

// In SecondActivity
class SecondActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var messageTextView: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        
        messageTextView = findViewById(R.id.messageTextView)
        
        // Get the same ViewModel instance
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        
        // Observe changes
        sharedViewModel.message.observe(this) { newMessage ->
            messageTextView.text = newMessage
        }
    }
}
```

### What is the Activity back stack?

The Activity back stack is a Last-In-First-Out (LIFO) stack that maintains the history of Activities
that the user has visited. When a new Activity is started, it's pushed onto the stack. When the user
presses the back button, the current Activity is popped from the stack and destroyed, and the
previous Activity resumes.

## Modern Android Development

### How does Jetpack Compose change the way we use Activities?

With Jetpack Compose, most apps adopt a single Activity architecture where navigation between
screens is handled by Compose's Navigation component rather than multiple Activities. The Activity
mainly serves as an entry point and container for the Compose UI.

**Practical Example:**

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            onNavigateToDetails = { itemId ->
                                navController.navigate("details/$itemId")
                            }
                        )
                    }
                    composable(
                        route = "details/{itemId}",
                        arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                        DetailsScreen(
                            itemId = itemId,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable("settings") {
                        SettingsScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Home Screen", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigateToDetails("item1") }) {
            Text("Navigate to Detail")
        }
    }
}

@Composable
fun DetailsScreen(itemId: String, onBackClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Details for item: $itemId", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Go Back")
        }
    }
}
```

### Why might you still need multiple Activities even when using Jetpack Compose?

Multiple Activities might still be needed for:

- Handling different entry points to your app (deep links, notifications)
- Implementing different visual presentation modes (e.g., a separate Activity for full-screen video
  playback)
- Using system-provided Activity-based APIs (like camera, document picker)
- Creating isolated task stacks for different user flows

## Best Practices

### What are some best practices when working with Activities?

- Avoid storing large data or running heavy operations in the Activity itself
- Always handle configuration changes properly to avoid data loss
- Use appropriate launch modes to control the Activity stack behavior
- Implement lifecycle-aware components to handle operations that should respect the Activity
  lifecycle
- In modern apps, consider using a single Activity architecture with Jetpack Compose or Fragments
- Ensure that critical data is saved in `onPause()` rather than `onStop()` or `onDestroy()`

### What are Activity Launch Modes and when would you use them?

Activity Launch Modes control how Activities are instantiated and placed on the back stack:

- **Standard**: Default mode. Creates a new instance of the Activity every time.
- **SingleTop**: If an instance of the Activity already exists at the top of the stack, it reuses
  that instance (calling `onNewIntent()`); otherwise creates a new instance.
- **SingleTask**: Creates a new task and places the Activity at the root of the task. If an instance
  already exists in another task, it brings that task to the foreground.
- **SingleInstance**: Similar to SingleTask but the Activity is the only one in its task. No other
  Activities can be part of that task.

**Practical Example:**

In AndroidManifest.xml:

```xml
<!-- Standard mode (default) -->
<activity android:name=".StandardActivity" />

<!-- SingleTop mode -->
<activity
    android:name=".SingleTopActivity"
    android:launchMode="singleTop" />

<!-- SingleTask mode -->
<activity
    android:name=".SingleTaskActivity"
    android:launchMode="singleTask" />

<!-- SingleInstance mode -->
<activity
    android:name=".SingleInstanceActivity"
    android:launchMode="singleInstance" />
```

In Kotlin code for SingleTop Activity:

```kotlin
class SingleTopActivity : AppCompatActivity() {
    private val TAG = "SingleTopActivity"
    private var instanceCount = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_top)
        instanceCount++
        
        findViewById<TextView>(R.id.instanceTextView).text = 
            "Instance #$instanceCount, Created at: ${System.currentTimeMillis()}"
            
        Log.d(TAG, "onCreate called, instance #$instanceCount")
        
        findViewById<Button>(R.id.launchSelfButton).setOnClickListener {
            startActivity(Intent(this, SingleTopActivity::class.java))
        }
    }
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent called instead of creating new instance")
        
        // Update UI to show we received a new intent
        findViewById<TextView>(R.id.statusTextView).text = 
            "Received new intent at: ${System.currentTimeMillis()}"
    }
}
```

### What is the difference between finish() and finishAffinity()?

- `finish()`: Closes only the current Activity
- `finishAffinity()`: Closes the current Activity and all Activities immediately below it in the
  same task that have the same affinity

### How would you preserve Activity state during configuration changes?

- Use `onSaveInstanceState()` to save lightweight UI state
- Use ViewModel to retain data across configuration changes
- Use `onRetainNonConfigurationInstance()` for complex objects (though this is largely replaced by
  ViewModel)
- Implement `android:configChanges` in the manifest to handle specific changes without recreating
  the Activity (use sparingly)
- For Jetpack Compose, use rememberSaveable for state preservation

**Practical Examples:**

1. **Using ViewModel:**

```kotlin
// ViewModel that survives configuration changes
class CounterViewModel : ViewModel() {
    private val _counter = MutableLiveData<Int>(0)
    val counter: LiveData<Int> = _counter
    
    fun increment() {
        _counter.value = (_counter.value ?: 0) + 1
    }
}

// Activity using the ViewModel
class ViewModelActivity : AppCompatActivity() {
    private lateinit var viewModel: CounterViewModel
    private lateinit var counterTextView: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel)
        
        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(CounterViewModel::class.java)
        
        counterTextView = findViewById(R.id.counterTextView)
        
        // Observe changes to counter
        viewModel.counter.observe(this) { count ->
            counterTextView.text = "Counter: $count"
        }
        
        findViewById<Button>(R.id.incrementButton).setOnClickListener {
            viewModel.increment()
        }
        
        // No need to restore state manually - ViewModel handles it!
    }
}
```

2. **Using onSaveInstanceState and onRestoreInstanceState:**

```kotlin
class SaveStateActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var counterTextView: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_state)
        
        counterTextView = findViewById(R.id.counterTextView)
        
        findViewById<Button>(R.id.incrementButton).setOnClickListener {
            counter++
            updateCounterDisplay()
        }
    }
    
    private fun updateCounterDisplay() {
        counterTextView.text = "Counter: $counter"
    }
    
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt("counter", 0)
        updateCounterDisplay()
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("counter", counter)
        super.onSaveInstanceState(outState)
    }
}
```

3. **Using configChanges in manifest:**

```xml
<activity
    android:name=".ConfigHandlingActivity"
    android:configChanges="orientation|screenSize" />
```

```kotlin
class ConfigHandlingActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var counterTextView: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_handling)
        
        counterTextView = findViewById(R.id.counterTextView)
        counterTextView.text = "Counter: $counter"
        
        findViewById<Button>(R.id.incrementButton).setOnClickListener {
            counter++
            counterTextView.text = "Counter: $counter"
        }
    }
    
    // This will be called instead of recreating the activity
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle configuration changes if needed
        val orientation = when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            Configuration.ORIENTATION_PORTRAIT -> "Portrait"
            else -> "Other"
        }
        Toast.makeText(this, "Orientation changed to: $orientation", Toast.LENGTH_SHORT).show()
    }
}
```

4. **Using rememberSaveable in Jetpack Compose:**

```kotlin
@Composable
fun SaveableCounterScreen() {
    var counter by rememberSaveable { mutableStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Counter: $counter",
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { counter++ }) {
            Text("Increment")
        }
    }
}