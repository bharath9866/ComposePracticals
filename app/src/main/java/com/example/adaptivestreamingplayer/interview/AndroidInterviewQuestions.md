### 1. (Android) What is ActivityManager?

`ActivityManager` is a system service in Android that provides information about and manages the activities, tasks, and processes running on the device. It is part of the Android framework, enabling developers to interact with and control aspects of the app lifecycle, memory usage, and task management. These are the key functions of ActivityManager:

1. **Task and Activity Information**: The `ActivityManager` can retrieve details about running tasks, activities, and their stack states. This helps developers monitor app behavior and system resource usage.

2. **Memory Management**: It provides information about memory usage across the system, including per-app memory consumption and system-wide memory states. Developers can use this to optimize app performance and handle low-memory conditions.

3. **App Process Management**: `ActivityManager` allows querying details about running app processes and services. Developers can use this information to detect app status or respond to process-level changes.

4. **Debugging and Diagnostics**: It provides tools for debugging, such as generating heap dumps or profiling apps, which can help identify performance bottlenecks or memory leaks.

#### Common Methods of ActivityManager

- **`getRunningAppProcesses()`**: Returns a list of processes currently running on the device.
- **`getMemoryInfo(ActivityManager.MemoryInfo memoryInfo)`**: This retrieves detailed memory information about the system, such as available memory, threshold memory, and whether the device is in a low-memory state. This is useful for optimizing app behavior during low-memory conditions.
- **`killBackgroundProcesses(String packageName)`**: This method terminates background processes for a specified app to free up system resources. It’s useful for testing or managing resource-intensive apps.
- **`isLowRamDevice()`**: Checks whether the device is categorized as low-RAM, helping apps optimize their resource usage for low-memory devices.
- **`appNotResponding(String message)`**: This method simulates an App Not Responding (ANR) event for testing purposes. It can be used during debugging to understand how an app behaves or responds during ANR situations.
- **`clearApplicationUserData()`**: This method clears all the user-specific data associated with the application, including files, databases, and shared preferences. It’s often used in cases like factory resets or resetting an app to its default state.

#### Example Usage

The code below demonstrates how to use `ActivityManager` to fetch memory information:

```kotlin
val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
val memoryInfo = ActivityManager.MemoryInfo()
activityManager.getMemoryInfo(memoryInfo)

Log.d(TAG, "Low memory state: ${memoryInfo.lowMemory}")
Log.d(TAG, "Threshold memory: ${memoryInfo.threshold / (1024 * 1024)} MB")
Log.d(TAG, "Threshold memory: ${memoryInfo.threshold / (1024 * 1024)} MB")

val processes = activityManager.runningAppProcesses
Log.d(TAG, "Process name: ${processes.first().processName}")

// Method for the app to tell system that it's wedged and would like to trigger an ANR.
activityManager.appNotResponding("Pokedex is not responding")

// Permits an application to erase its own data from disk.
activityManager.clearApplicationUserData()
```

#### ActivityManager in LeakCanary

[LeakCanary](https://square.github.io/leakcanary/) is an open-source memory leak detection library for Android applications, developed by Block. It automatically monitors and detects memory leaks in your app during development, providing detailed analysis and actionable insights to help you fix leaks efficiently. [It utilizes ActivityManager](https://github.com/square/leakcanary/blob/02d0d8b6ebfe8de55c109b904d7b526063f3f852/leakcanary/leakcanary-android-process/src/main/java/leakcanary/LeakCanaryProcess.kt#L75) for tracing the memory states and information.

#### Summary

`ActivityManager` is for system-level management, performance tuning, and monitoring app behavior. While its functionality has been partially superseded by more specialized APIs in modern Android, it remains a tool for managing and optimizing resource usage in Android applications. Developers can use it responsibly to avoid unintended system performance impacts.

### 2. (Kotlin) What happens if you run null + null operation?

Adding `null + null` does not result in a compiler error. Instead, it returns the string `"nullnull"`. This happens because Kotlin implicitly converts `null` to its `String` representation when the `+` operator is used. In this context, the `+` operator concatenates the string `"null"` with another `"null"`, producing `"nullnull"`.

```kotlin
val result = null + null
println(result) // Output: nullnull
```

This behavior is possible because the `+` operator for `String` types is overloaded to perform string concatenation, and `null`, when used in a string context, is converted to the string `"null"`.

#### Why Does This Happen?

The Kotlin compiler interprets the `+` operator as a call to the `String.plus()` function if you were not overridden the operator. When either operand is `null`, Kotlin converts it into the string `"null"`. Here’s what effectively happens under the hood:

```kotlin
val result = "null".plus("null")
println(result) // Output: nullnull
```

#### Summary

In Kotlin, `null + null` results in `"nullnull"` due to implicit conversion of `null` to its string representation during string concatenation. This highlights the importance of understanding how Kotlin handles nullable values in different contexts to avoid unexpected results in your code. For more information, you can refer to [What Happens if You Add `null + null` in Kotlin?](https://youtu.be/wwplVknTza4?feature=shared).

### 3. (Compose) What is composition and how to create it?

[Composition](https://developer.android.com/develop/ui/compose/lifecycle#composition-anatomy) represents the UI of your app and is generated by executing Composable functions. It organizes the UI into a tree structure of Composables, leveraging a **Composer** to create and manage this tree dynamically. The Composition records the state and applies necessary changes to the node tree to update the UI efficiently, a process known as **recomposition**. In essence, Composition serves as the backbone of Jetpack Compose, managing both the UI structure and the state of Composable functions at runtime.

`Short Form of Composition for Interview Answering`: 
1. Composition represents the UI of app and is generated by executing composable functions.
2. It organizes the UI into a tree structure of Composables, leveraging a **Composer** to create and manage this tree dynamically.
3. The Composition records the state and applies necessary changes to the node tree to update the UI efficiently, a process known as **recomposition**.
4. 

#### Creating a Composition

A **Composition** refers to the process of converting Composable functions into a UI hierarchy that can be rendered on the screen. The Composition is at the core of how Jetpack Compose works, enabling the framework to track changes in state and update the UI efficiently. Here’s how you can create and manage a Composition:

#### Using the `ComponentActivity.setContent` Function

The most common way to create a Composition is by using the `setContent` function, which is provided by the `ComponentActivity` or `ComposeView`. This function initializes the Composition and defines the content to be displayed within it.

```kotlin
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposableContent()
        }
    }
}

@Composable
fun MyComposableContent() {
    // Define your UI components here
}
```

In this example:
- The `setContent` function creates the Composition.
- `MyComposableContent` is a Composable function that defines the UI to render.

However, if you examine the internal implementation of `ComponentActivity.setContent`, you’ll notice that it ultimately utilizes `ComposeView.setContent` to initialize and create the Composition:

```kotlin
public fun ComponentActivity.setContent(
    parent: CompositionContext? = null,
    content: @Composable () -> Unit
) {
    val existingComposeView = window.decorView
        .findViewById<ViewGroup>(android.R.id.content)
        .getChildAt(0) as? ComposeView

    if (existingComposeView != null) with(existingComposeView) {
        setParentCompositionContext(parent)
        setContent(content)
    } else ComposeView(this).apply {
        // Set content and parent **before** setContentView
        // to have ComposeView create the composition on attach
        setParentCompositionContext(parent)
        setContent(content)
        // Set the view tree owners before setting the content view so that the inflation process
        // and attach listeners will see them already present
        setOwners()
        setContentView(this, DefaultActivityContentLayoutParams)
    }
}
```

#### Embedding Compose in XML Layouts

If you want to integrate Compose into a traditional Android View hierarchy, you can use `ComposeView`. This allows you to create a Composition within an XML-defined layout.

```kotlin
import androidx.compose.ui.platform.ComposeView

val composeView = ComposeView(context).apply {
    setContent {
        MyComposableContent()
    }
}
```

You can add this `ComposeView` to an existing ViewGroup or include it in your XML layouts using the `<androidx.compose.ui.platform.ComposeView>` tag.

#### Summary

Composition is the process of building and managing a hierarchical structure of the UI by executing Composable functions. Creating a Composition involves defining your UI with Composable functions and initializing the Composition using mechanisms like `ComponentActivity.setContent`, or `ComposeView.setContent`.

### 4. (Android) How do you store and persist data locally?
Android provides several mechanisms for storing and persisting data locally, each designed for specific use cases such as lightweight key-value storage, structured database management, or file handling. Below are the primary options for local storage:

#### SharedPreferences

[SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) is a simple key-value storage mechanism best suited for lightweight data, such as app settings or user preferences. It allows you to save primitive data types like `Boolean`, `Int`, `String`, and `Float` and persists them across app restarts. SharedPreferences operates synchronously, but with the introduction of DataStore, it’s becoming less favored for modern applications.

```kotlin
val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
val editor = sharedPreferences.edit {
  putString("user_name", "skydoves")
}
```

#### DataStore

[Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) is a more modern, scalable, and efficient replacement for SharedPreferences. It provides two types: `PreferencesDataStore` for key-value storage and `ProtoDataStore` for structured data. Unlike SharedPreferences, DataStore is asynchronous, avoiding potential issues with blocking the main thread.

```kotlin
val dataStore: DataStore<Preferences> = context.createDataStore(name = "settings")

val userNameKey = stringPreferencesKey("user_name")
runBlocking {
    dataStore.edit { settings ->
        settings[userNameKey] = "John Doe"
    }
}
```

#### Room Database

[Room Database](https://developer.android.com/training/data-storage/room) is a high-level abstraction over SQLite, designed for handling structured and relational data. It simplifies database management with annotations, compile-time checks, and LiveData or Flow support for reactive programming. Room is ideal for apps requiring complex queries or large amounts of structured data.

```kotlin
@Entity
data class User(
    @PrimaryKey val id: Int,
    val name: String
)

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUserById(id: Int): User
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

#### File Storage

For binary or custom data, Android allows you to store files in internal or external storage. Internal storage is private to your app, while external storage can be shared with other apps. File I/O operations can be used for tasks like storing images, videos, or custom serialized data.

```kotlin
val file = File(context.filesDir, "user_data.txt")
file.writeText("Sample user data")
```

#### Choosing the Right Solution

The choice of storage mechanism depends on the nature and complexity of the data:
- Use SharedPreferences or DataStore for lightweight, non-relational data such as settings or flags.
- Use Room for complex relational data with query requirements.
- Use File Storage for binary files or large custom datasets.

Each mechanism offers specific advantages tailored to different data management needs, ensuring efficient and reliable data storage on Android.

## 💡 Tips With Code

### The `runBlocking` Internals

`runBlocking` creates a new coroutine that blocks the current thread until all tasks within it complete. It is mainly used in main functions or unit tests to keep the application alive while executing coroutines. If this blocked thread is interrupted, then the coroutine job is cancelled and this `runBlocking` invocation throws `InterruptedException`.

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Starting runBlocking")
    launch {
        delay(1000L)
        println("Inside coroutine")
    }
    println("Ending runBlocking")
}
```

You might used to see `runBlocking` has been used frequently in the offical docs or unit testing. However, using `runBlocking` in Android UI code is **discouraged** because it blocks the main thread, causing the app to freeze. Since Android prioritizes responsive UI interactions, suspending functions should be launched using `lifecycleScope.launch` or `viewModelScope.launch` to avoid blocking the main thread.

If you examine the internal implementation of `runBlocking`, you'll find that it launches a new coroutine on the current thread (such as the Android main thread) while leveraging the global scope to derive the coroutine context.

```kotlin
public actual fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    val currentThread = Thread.currentThread()
    val contextInterceptor = context[ContinuationInterceptor]
    val eventLoop: EventLoop?
    val newContext: CoroutineContext
    if (contextInterceptor == null) {
        // create or use private event loop if no dispatcher is specified
        eventLoop = ThreadLocalEventLoop.eventLoop
        newContext = GlobalScope.newCoroutineContext(context + eventLoop)
    } else {
        // See if context's interceptor is an event loop that we shall use (to support TestContext)
        // or take an existing thread-local event loop if present to avoid blocking it (but don't create one)
        eventLoop = (contextInterceptor as? EventLoop)?.takeIf { it.shouldBeProcessedFromContext() }
            ?: ThreadLocalEventLoop.currentOrNull()
        newContext = GlobalScope.newCoroutineContext(context)
    }
    val coroutine = BlockingCoroutine<T>(newContext, currentThread, eventLoop)
    coroutine.start(CoroutineStart.DEFAULT, coroutine, block)
    return coroutine.joinBlocking()
}
```

It initializes a new instance of `BlockingCoroutine`, and upon examining the internal implementation of `BlockingCoroutine`, particularly the `joinBlocking` method, it becomes evident that this method blocks and occupies the main thread entirely until all tasks are completed.

```kotlin
import java.util.concurrent.locks.LockSupport.parkNanos

private class BlockingCoroutine<T>(
    parentContext: CoroutineContext,
    private val blockedThread: Thread,
    private val eventLoop: EventLoop?
) : AbstractCoroutine<T>(parentContext, true, true) {

    override val isScopedCoroutine: Boolean get() = true

    override fun afterCompletion(state: Any?) {
        // wake up blocked thread
        if (Thread.currentThread() != blockedThread)
            unpark(blockedThread)
    }

    @Suppress("UNCHECKED_CAST")
    fun joinBlocking(): T {
        registerTimeLoopThread()
        try {
            eventLoop?.incrementUseCount()
            try {
                while (true) {
                    @Suppress("DEPRECATION")
                    if (Thread.interrupted()) throw InterruptedException().also { cancelCoroutine(it) }
                    val parkNanos = eventLoop?.processNextEvent() ?: Long.MAX_VALUE
                    // note: process next even may loose unpark flag, so check if completed before parking
                    if (isCompleted) break
                    parkNanos(this, parkNanos)
                }
            } finally { // paranoia
                eventLoop?.decrementUseCount()
            }
        } finally { // paranoia
            unregisterTimeLoopThread()
        }
        // now return result
        val state = this.state.unboxState()
        (state as? CompletedExceptionally)?.let { throw it.cause }
        return state as T
    }
}
```

For this reason, it is crucial to use `runBlocking` with caution to prevent it from occupying the Android main thread, which could lead to ANR (Application Not Responding) issues in your application.