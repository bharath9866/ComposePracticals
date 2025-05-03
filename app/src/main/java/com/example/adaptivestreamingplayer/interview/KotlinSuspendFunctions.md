# Understanding Kotlin Suspend Functions: A Comprehensive Guide

Suspend functions are one of the cornerstone features of Kotlin's coroutines framework, enabling
asynchronous programming that is both powerful and readable. This guide covers suspend functions in
detail with practical examples.

## Table of Contents

- [Introduction to Suspend Functions](#introduction-to-suspend-functions)
- [Basic Concepts](#basic-concepts)
- [How Suspension Works](#how-suspension-works)
- [Working with Coroutine Contexts](#working-with-coroutine-contexts)
- [Sequential vs Parallel Execution](#sequential-vs-parallel-execution)
- [Error Handling](#error-handling)
- [Cancellation and Timeouts](#cancellation-and-timeouts)
- [Composing Suspend Functions](#composing-suspend-functions)
- [Practical Use Cases and Patterns](#practical-use-cases-and-patterns)
- [Integration with Flow](#integration-with-flow)
- [Best Practices](#best-practices)

## Introduction to Suspend Functions

Suspend functions are functions that can be paused and resumed at specific points
without blocking the thread they're running on. This allows the thread to perform other work
while the suspend function is waiting for an operation (like I/O or a timer) to complete.

Regular functions, in contrast, must run to completion before returning control, potentially
blocking the thread if they perform long-running operations.

Here's a simple example illustrating the concept:

```kotlin
import kotlinx.coroutines.*

// A function simulating a long-running task without blocking the thread
suspend fun doSomethingUseful(): String {
    println("Task started on thread: ${Thread.currentThread().name}")
    delay(1000L) // Coroutine is suspended here, but the thread is free
    println("Task finished on thread: ${Thread.currentThread().name}")
    return "Result of the task"
}

fun main() = runBlocking { // Creates a coroutine scope for the example
    println("Main started on thread: ${Thread.currentThread().name}")

    val job = launch { // Launch a new coroutine
        val result = doSomethingUseful() // Call the suspend function
        println("Received result: $result")
    }

    println("Main continues while the task runs in the background...")
    delay(500L) // Main coroutine does some other work (or waits)
    println("Main still running...")

    job.join() // Wait for the launched coroutine to finish
    println("Main finished.")
}

/* 
Possible output:
Main started on thread: main
Task started on thread: main
Main continues while the task runs in the background...
Main still running...
(after ~500ms more)
Task finished on thread: main 
Received result: Result of the task
Main finished.

Note: Even though 'delay(1000L)' pauses the execution of doSomethingUseful for 1 second,
the 'Main continues...' and 'Main still running...' messages are printed because the 'main' 
thread wasn't blocked by the delay. The coroutine scheduler manages the suspension and resumption.
*/
```

### The Problem Suspend Functions Solve

Traditional asynchronous programming often leads to complex callback structures or reactive streams
that can be difficult to read and maintain:

```kotlin
// Traditional callback approach
fun fetchUserData(userId: String, callback: (UserData) -> Unit) {
    networkService.fetchUser(userId) { userData ->
        databaseService.cacheUser(userData) { 
            analyticsService.logUserFetch(userData) {
                callback(userData) // Finally call back with the result
            }
        }
    }
}

// Usage
fetchUserData("user123") { userData ->
    updateUI(userData)
}
```

With suspend functions, the same asynchronous operations become sequential and readable:

```kotlin
// Using suspend functions
suspend fun fetchUserData(userId: String): UserData {
    val userData = networkService.fetchUser(userId)
    databaseService.cacheUser(userData)
    analyticsService.logUserFetch(userData)
    return userData
}

// Usage
lifecycleScope.launch {
    val userData = fetchUserData("user123")
    updateUI(userData)
}
```

## Basic Concepts

### Declaring and Using Suspend Functions

A suspend function is declared using the `suspend` keyword:

```kotlin
suspend fun fetchData(): Data {
    delay(1000) // A suspend function from kotlinx.coroutines
    return Data("Some data")
}
```

Key points about suspend functions:

1. They can call other suspend functions
2. They must be called from a coroutine or another suspend function
3. They can use regular functions freely
4. Regular functions cannot directly call suspend functions

### Simple Example: Network Request

```kotlin
// Define a suspend function for a network request
suspend fun fetchUserProfile(userId: String): UserProfile {
    delay(1000) // Simulates network delay without blocking the thread
    return UserProfile(userId, "John Doe", "john@example.com")
}

// Using the suspend function
fun loadUserData(userId: String) {
    viewModelScope.launch {
        try {
            val userProfile = fetchUserProfile(userId) // Suspension point
            updateUI(userProfile)
        } catch (e: Exception) {
            showError(e.message)
        }
    }
}
```

### Execution Environment: Coroutine Builders

To call suspend functions, you need a coroutine. Common coroutine builders include:

```kotlin
// Launch a coroutine that doesn't return a result
fun startOperation() {
    viewModelScope.launch {
        val result = fetchData() // Calling our suspend function
        processResult(result)
    }
}

// Launch a coroutine that returns a result
fun getResult(): Deferred<String> {
    return viewModelScope.async {
        val data = fetchData()
        processData(data) // Returns a String
    }
}

// Block the current thread (avoid in production code, especially on UI thread)
fun getBlocking(): String {
    return runBlocking {
        fetchData().toString()
    }
}
```

## How Suspension Works

### Understanding the Suspension Mechanism

When a suspend function encounters a suspension point (like `delay()` or another suspend function
call), it:

1. Saves its execution state
2. Releases the thread it's running on
3. When the suspension is over, it resumes execution (potentially on a different thread)

This diagram illustrates the concept:

```
Thread 1: [----fetchUserProfile----]       [----continue execution----]
                                    \     /
                                     pause
                                           
                                    (Thread released for other work)
```

### Decompiled Suspend Function

Under the hood, the Kotlin compiler transforms suspend functions using continuations. Here's a
simplified view of what happens:

```kotlin
// Our original suspend function
suspend fun fetchUserData(userId: String): UserData {
    val userInfo = fetchUserInfo(userId)
    val userPreferences = fetchUserPreferences(userId)
    return UserData(userInfo, userPreferences)
}

// Conceptually transforms to something like this (simplified)
fun fetchUserData(userId: String, continuation: Continuation<UserData>): Any {
    val state = continuation.state
    
    return when (state) {
        0 -> {
            // First call, start with fetchUserInfo
            val newContinuation = createContinuation(continuation, state = 1)
            fetchUserInfo(userId, newContinuation)
            COROUTINE_SUSPENDED
        }
        1 -> {
            // Resumed after fetchUserInfo completed
            val userInfo = continuation.result
            val newContinuation = createContinuation(continuation, state = 2)
            fetchUserPreferences(userId, newContinuation)
            COROUTINE_SUSPENDED
        }
        2 -> {
            // Resumed after fetchUserPreferences completed
            val userInfo = continuation.savedUserInfo
            val userPreferences = continuation.result
            UserData(userInfo, userPreferences)
        }
    }
}
```

This state machine approach allows functions to pause and resume without blocking threads.

### Practical Example: Deep Dive into Suspension

Let's break down suspension by tracing through an example with `delay()`:

```kotlin
suspend fun processRequest() {
    println("Processing started on ${Thread.currentThread().name}")
    delay(1000) // Suspension point - the thread is released here
    println("Processing resumed on ${Thread.currentThread().name}") // May be a different thread
    
    // Demonstrate another suspension point
    val result = withContext(Dispatchers.IO) {
        println("Fetching data on ${Thread.currentThread().name}")
        fetchFromNetwork()
    }
    
    println("Back to ${Thread.currentThread().name} with result: $result")
}
```

When run in a coroutine on the Main dispatcher:

```
Processing started on main
// After 1000ms
Processing resumed on main
Fetching data on DefaultDispatcher-worker-1
// After network operation
Back to main with result: NetworkResult
```

## Working with Coroutine Contexts

Suspend functions inherit the context (including the dispatcher) from their calling coroutine, but
can switch contexts when needed.

### Dispatchers

Dispatchers control which thread pool the coroutine runs on:

- `Dispatchers.Main`: For UI operations
- `Dispatchers.IO`: Optimized for IO operations
- `Dispatchers.Default`: For CPU-intensive tasks
- `Dispatchers.Unconfined`: Runs in the current thread until first suspension

### Context Switching

The `withContext()` function allows a suspend function to switch contexts temporarily:

```kotlin
suspend fun loadAndProcessImage(imageUrl: String): ProcessedImage {
    // Step 1: Network request (IO dispatcher)
    val imageData = withContext(Dispatchers.IO) {
        networkClient.fetchImage(imageUrl)
    }
    
    // Step 2: Process image (CPU-intensive on Default dispatcher)
    val processed = withContext(Dispatchers.Default) {
        applyFilters(imageData)
    }
    
    // Step 3: Save to disk (IO dispatcher)
    withContext(Dispatchers.IO) {
        diskCache.saveImage(processed)
    }
    
    // Returns to the original dispatcher
    return processed
}

// Usage in UI context
lifecycleScope.launch(Dispatchers.Main) {
    val image = loadAndProcessImage("https://example.com/image.jpg")
    imageView.setImage(image) // Back on Main thread
}
```

This approach ensures efficient resource usage and responsiveness by:

- Performing network and disk operations on IO dispatcher
- Running image processing on CPU-optimized threads
- Returning to the UI thread for view updates

### Custom Coroutine Contexts

You can create custom elements for the coroutine context and pass them to a suspend function's
calling coroutine:

```kotlin
// Custom element to track user operation
class UserOperation(val userId: String) : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<UserOperation>
    override val key: CoroutineContext.Key<UserOperation> get() = Key
}

// Accessing the context element in a suspend function
suspend fun trackableOperation() {
    val userId = coroutineContext[UserOperation]?.userId ?: "anonymous"
    println("Performing operation for user: $userId")
    // Do work...
}

// Using the context
lifecycleScope.launch(Dispatchers.Main + UserOperation("user123")) {
    trackableOperation()
}
```

## Sequential vs Parallel Execution

### Sequential Execution

Suspend functions called one after another execute sequentially:

```kotlin
suspend fun fetchMultipleResources() {
    val startTime = System.currentTimeMillis()
    
    // These execute in sequence - total time is the sum of all operations
    val users = fetchUsers()          // Takes 1000ms
    val products = fetchProducts()    // Takes 1000ms
    val settings = fetchSettings()    // Takes 1000ms
    
    val totalTime = System.currentTimeMillis() - startTime
    println("Sequential fetch took $totalTime ms") // ~3000ms
    
    processResults(users, products, settings)
}
```

### Parallel Execution with async/await

For concurrent operations, use `async` to start multiple operations in parallel and `await` to get
their results:

```kotlin
suspend fun fetchMultipleResourcesConcurrently() {
    val startTime = System.currentTimeMillis()
    
    // Start all operations concurrently
    val usersDeferred = coroutineScope {
        async { fetchUsers() }       // Starts immediately
    }
    val productsDeferred = coroutineScope {
        async { fetchProducts() }    // Starts immediately
    }
    val settingsDeferred = coroutineScope {
        async { fetchSettings() }    // Starts immediately
    }
    
    // Wait for all results (only blocks the coroutine, not the thread)
    val users = usersDeferred.await()
    val products = productsDeferred.await() 
    val settings = settingsDeferred.await()
    
    val totalTime = System.currentTimeMillis() - startTime
    println("Parallel fetch took $totalTime ms") // ~1000ms (the time of the slowest operation)
    
    processResults(users, products, settings)
}
```

### Running Multiple Operations with Structured Concurrency

Structured concurrency ensures that when a coroutine scope completes, all its child coroutines
complete too:

```kotlin
suspend fun loadDashboardData(): DashboardData = coroutineScope {
    // If any child coroutine fails, all others are cancelled
    
    val users = async { userService.fetchUsers() }
    val products = async { productService.fetchProducts() }
    val notifications = async { notificationService.fetchNotifications() }
    
    try {
        // Create dashboard with all data
        DashboardData(
            users = users.await(),
            products = products.await(),
            notifications = notifications.await()
        )
    } catch (e: Exception) {
        // Any exception from any child will be caught here
        // All other children will be cancelled automatically
        throw DashboardLoadException("Failed to load dashboard", e)
    }
}
```

The key here is `coroutineScope` which:

1. Creates a new coroutine scope
2. Waits for all children to complete
3. Propagates cancellation to children
4. Only completes when all children complete

### Handling Collections in Parallel

Process collections concurrently using `async`:

```kotlin
suspend fun processUserUrls(userIds: List<String>): List<Profile> = coroutineScope {
    // Map each ID to an async operation and collect results
    userIds.map { userId ->
        async {
            fetchUserProfile(userId)
        }
    }.awaitAll() // Waits for all operations and returns a list of results
}

// When you need to limit the concurrency level
suspend fun processWithLimitedConcurrency(items: List<Item>, concurrencyLevel: Int = 10): List<Result> = coroutineScope {
    // Custom semaphore to limit concurrency
    val semaphore = Semaphore(concurrencyLevel)
    
    items.map { item ->
        async {
            semaphore.withPermit {
                // Only concurrencyLevel operations will run simultaneously
                processItem(item)
            }
        }
    }.awaitAll()
}
```

## Error Handling

### Basic Try-Catch

Exceptions in suspend functions propagate like in regular functions:

```kotlin
suspend fun fetchSafeUserData(userId: String): UserData? {
    return try {
        api.fetchUserData(userId)
    } catch (e: IOException) {
        Log.e("Network", "Network error when fetching user $userId", e)
        null
    } catch (e: Exception) {
        Log.e("General", "Error fetching user $userId", e)
        throw UserFetchException("Could not fetch user data", e)
    }
}
```

### Structured Concurrency and Error Propagation

With structured concurrency, errors propagate through the coroutine hierarchy:

```kotlin
suspend fun loadUserSettings(): UserSettings = coroutineScope {
    try {
        // If any of these throws an exception, it propagates up
        val preferences = async { api.fetchPreferences() }
        val theme = async { api.fetchTheme() }
        val notifications = async { api.fetchNotificationSettings() }
        
        UserSettings(
            preferences.await(),
            theme.await(),
            notifications.await()
        )
    } catch (e: Exception) {
        // Handle all errors from any child coroutine
        Log.e("Settings", "Failed to load settings", e)
        throw SettingsLoadException("Could not load settings", e)
    }
}
```

### Error Handling with SupervisorScope

If you want child coroutines to fail independently without affecting siblings:

```kotlin
suspend fun loadDashboardParts(): DashboardData = supervisorScope {
    val users = async { 
        try {
            userService.fetchUsers()
        } catch (e: Exception) {
            Log.e("Dashboard", "Failed to load users", e)
            emptyList() // Provide fallback
        }
    }
    
    val recommendations = async {
        try {
            recommendationService.fetchRecommendations()
        } catch (e: Exception) {
            Log.e("Dashboard", "Failed to load recommendations", e)
            emptyList() // Provide fallback  
        }
    }
    
    // Even if one fails, the other continues
    DashboardData(
        users = users.await(),
        recommendations = recommendations.await()
    )
}
```

### Result Pattern for Error Handling

Using the Kotlin Result type can make error handling more explicit:

```kotlin
suspend fun fetchUserResult(userId: String): Result<User> {
    return try {
        val user = api.fetchUser(userId)
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

// Usage
suspend fun displayUser(userId: String) {
    fetchUserResult(userId).fold(
        onSuccess = { user ->
            showUser(user)
        },
        onFailure = { error ->
            showError("Could not load user: ${error.message}")
        }
    )
}
```

## Cancellation and Timeouts

### Cancellation Basics

Coroutines can be cancelled, and suspend functions should respect cancellation:

```kotlin
val job = viewModelScope.launch {
    try {
        val result = longRunningOperation()
        processResult(result)
    } catch (e: CancellationException) {
        // Clean up resources if needed
        println("Operation was cancelled")
    }
}

// Later: cancel the coroutine
job.cancel()
```

### Making Functions Cancellation-Aware

Cooperative cancellation checks if the coroutine is still active:

```kotlin
suspend fun cancellableLongTask(): Result {
    // Regular suspension points like delay() check for cancellation automatically
    delay(100)
    
    // For CPU-intensive work, check cancellation manually
    var progress = 0
    while (progress < 100 && isActive) { // isActive is a property from coroutineContext
        doSomeWork()
        progress++
        
        yield() // Explicit cancellation check point
    }
    
    return if (isActive) Result.Success else Result.Cancelled
}
```

### Timeouts

Suspend functions can use timeouts to automatically cancel if they take too long:

```kotlin
suspend fun fetchWithTimeout(url: String): Data {
    return withTimeout(5000) { // 5 seconds timeout
        api.fetchData(url)
    }
}

// Or with a fallback
suspend fun fetchWithTimeoutOrNull(url: String): Data? {
    return withTimeoutOrNull(5000) {
        api.fetchData(url)
    } // Returns null on timeout instead of throwing
}

// Using in practice
suspend fun getDataWithRetries(url: String, maxRetries: Int = 3): Result<Data> {
    var attempts = 0
    var lastException: Exception? = null
    
    while (attempts < maxRetries) {
        try {
            return Result.success(withTimeout(5000) {
                api.fetchData(url)
            })
        } catch (e: TimeoutCancellationException) {
            lastException = e
            attempts++
            delay(attempts * 1000L) // Exponential backoff
        } catch (e: Exception) {
            return Result.failure(e) // Other exceptions are not retried
        }
    }
    
    return Result.failure(lastException ?: RuntimeException("Unknown error"))
}
```

## Composing Suspend Functions

### Building Higher-Level Functions

Create new suspend functions by composing other suspend functions:

```kotlin
// Low-level operations
suspend fun fetchUserData(userId: String): UserData = withContext(Dispatchers.IO) {
    api.getUserData(userId)
}

suspend fun fetchUserPosts(userId: String): List<Post> = withContext(Dispatchers.IO) {
    api.getUserPosts(userId)
}

suspend fun fetchUserFollowers(userId: String): List<User> = withContext(Dispatchers.IO) {
    api.getUserFollowers(userId)
}

// Higher-level composition
suspend fun fetchUserProfile(userId: String): UserProfile {
    // Run operations in parallel
    return coroutineScope {
        val userData = async { fetchUserData(userId) }
        val userPosts = async { fetchUserPosts(userId) }
        val userFollowers = async { fetchUserFollowers(userId) }
        
        // Combine results
        UserProfile(
            user = userData.await(),
            recentPosts = userPosts.await().take(5),
            followerCount = userFollowers.await().size
        )
    }
}
```

### Extract Common Patterns into Reusable Functions

Create utility suspend functions to encapsulate common patterns:

```kotlin
// Retry utility
suspend fun <T> retry(
    times: Int = 3,
    initialDelay: Long = 100,
    maxDelay: Long = 1000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            // Handle specific exceptions differently if needed
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}

// Usage
suspend fun fetchImportantData(): Data = retry(times = 5) {
    api.fetchData()
}
```

### Function Composition with Extension Functions

Create extension functions on existing suspend functions to add behavior:

```kotlin
// Add logging to any suspend function
suspend fun <T> (suspend () -> T).withLogging(tag: String): T {
    val startTime = System.currentTimeMillis()
    try {
        Log.d(tag, "Starting operation")
        val result = this()
        val endTime = System.currentTimeMillis()
        Log.d(tag, "Operation completed in ${endTime - startTime}ms")
        return result
    } catch (e: Exception) {
        Log.e(tag, "Operation failed with exception", e)
        throw e
    }
}

// Usage
suspend fun loadData() {
    val result = ::fetchUserProfile.withLogging("ProfileLoader")
}
```

## Practical Use Cases and Patterns

### Android View Model

```kotlin
class UserProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState = _userState.asStateFlow()
    
    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                val userProfile = userRepository.fetchUserProfile(userId)
                _userState.value = UserState.Success(userProfile)
            } catch (e: Exception) {
                _userState.value = UserState.Error("Failed to load profile: ${e.message}")
            }
        }
    }
}

// Supporting sealed class for UI state
sealed class UserState {
    object Loading : UserState()
    data class Success(val profile: UserProfile) : UserState()
    data class Error(val message: String) : UserState()
}
```

### Repository Pattern with Caching

```kotlin
class ProductRepository(
    private val api: ProductApi,
    private val dao: ProductDao
) {
    suspend fun getProduct(id: String, forceRefresh: Boolean = false): Product {
        // Check if we should use cache
        if (!forceRefresh) {
            val cachedProduct = dao.getProduct(id)
            if (cachedProduct != null) {
                return cachedProduct
            }
        }
        
        // Fetch from network
        return withContext(Dispatchers.IO) {
            try {
                val product = api.fetchProduct(id)
                // Cache in database
                dao.insertProduct(product)
                product
            } catch (e: Exception) {
                // Try to get from cache as fallback
                val cachedProduct = dao.getProduct(id)
                if (cachedProduct != null) {
                    return@withContext cachedProduct
                }
                throw e // Re-throw if no cache available
            }
        }
    }
    
    suspend fun searchProducts(query: String): List<Product> = withContext(Dispatchers.IO) {
        try {
            val results = api.searchProducts(query)
            dao.insertAll(results)
            results
        } catch (e: IOException) {
            // On network error, fall back to cache with matching query
            dao.searchProducts("%$query%")
        }
    }
}
```

### Background Task Processing

```kotlin
class ImageProcessor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun processImage(uri: Uri): ProcessedImage = withContext(dispatcher) {
        // Read the bitmap
        val bitmap = withContext(Dispatchers.IO) {
            context.contentResolver.openInputStream(uri).use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
        
        // Apply CPU-intensive filters
        val processed = applyFilters(bitmap)
        
        // Save result
        withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, "processed_${System.currentTimeMillis()}.jpg")
            file.outputStream().use { out ->
                processed.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            ProcessedImage(processed, file.path)
        }
    }
    
    private suspend fun applyFilters(bitmap: Bitmap): Bitmap {
        var result = bitmap
        
        result = applyBlur(result)
        // Check cancellation between operations
        ensureActive()
        
        result = applyColorFilter(result)
        ensureActive()
        
        result = applyContrast(result)
        
        return result
    }
    
    // Implement filter functions...
}
```

### Networking With Retrofit

```kotlin
// Define API service with suspend functions
interface ApiService {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): UserResponse
    
    @POST("users")
    suspend fun createUser(@Body user: UserRequest): UserResponse
}

// Use in repository
class ApiRepository(private val apiService: ApiService) {
    suspend fun fetchUser(userId: String): Result<User> {
        return try {
            val response = apiService.getUser(userId)
            Result.success(response.toUser())
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> Result.failure(UserNotFoundException(userId))
                else -> Result.failure(ApiException("API error: ${e.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(NetworkException("Network error: ${e.message}"))
        }
    }
}
```

## Integration with Flow

### Converting Suspend Functions to Flow

```kotlin
// Single value suspend function
suspend fun fetchUser(userId: String): User {
    delay(1000)
    return User(userId, "John")
}

// Convert to Flow that emits once
fun getUserAsFlow(userId: String): Flow<User> = flow {
    emit(fetchUser(userId))
}

// Create polling Flow from suspend function
fun pollUser(userId: String, interval: Long = 5000): Flow<User> = flow {
    while (true) {
        val user = fetchUser(userId)
        emit(user)
        delay(interval)
    }
}
```

### Processing Flow with Suspend Functions

```kotlin
// Process each item with a suspend function
suspend fun processUserUpdates(userId: String) {
    getUserUpdatesFlow(userId)
        .map { update -> processUpdate(update) } // Apply suspend function to each item
        .collect { processedUpdate ->
            displayUpdate(processedUpdate)
        }
}

// Use a suspend function with Flow combinators
suspend fun loadDataForDashboard(userId: String): Flow<DashboardData> {
    return combine(
        userFlow(userId),
        notificationsFlow(userId),
        newsFlow()
    ) { user, notifications, news ->
        // Apply suspend function to combined emissions
        createDashboardData(user, notifications, news)
    }
}

// The suspend function used above
suspend fun createDashboardData(user: User, notifications: List<Notification>, news: List<NewsItem>): DashboardData {
    // Perform additional async operations if needed
    val userPreferences = fetchUserPreferences(user.id)
    return DashboardData(user, notifications, news, userPreferences)
}
```

### Flow with Timeout and Retry

```kotlin
val userUpdates: Flow<UserUpdate> = flow {
    // Implementation
}
.retry(3) { cause -> cause is IOException } // Retry 3 times on IO exceptions
.timeout(5000) // Timeout after 5 seconds
.onStart { emit(UserUpdate.Loading) }
.catch { cause -> emit(UserUpdate.Error("Failed to load updates: ${cause.message}")) }
```

## Best Practices

### 1. Make Functions Suspendable at the Right Level

Bad:

```kotlin
// Too granular, makes calling code complex
suspend fun validateEmail(email: String): Boolean {
    delay(100) // Simulate validation
    return email.contains("@")
}

suspend fun validatePassword(password: String): Boolean {
    delay(100)
    return password.length >= 8
}

// Usage requires multiple coroutine calls
suspend fun validateCredentials(email: String, password: String) {
    val validEmail = validateEmail(email)
    val validPassword = validatePassword(password)
    // ...
}
```

Better:

```kotlin
// Higher-level function is suspendable
suspend fun validateCredentials(email: String, password: String): Boolean {
    // Internal functions don't need to be suspendable
    fun validateEmail(email: String): Boolean = email.contains("@")
    fun validatePassword(password: String): Boolean = password.length >= 8
    
    // Only add suspension if needed
    val complexCheck = withContext(Dispatchers.Default) {
        performComplexValidation(email, password)
    }
    
    return validateEmail(email) && validatePassword(password) && complexCheck
}
```

### 2. Use the Right Context and Dispatcher

```kotlin
suspend fun processImage(bitmap: Bitmap): Bitmap {
    // CPU-intensive work should use Default dispatcher
    return withContext(Dispatchers.Default) {
        applyFilters(bitmap)
    }
}

suspend fun saveFile(data: ByteArray, fileName: String) {
    // I/O operations should use IO dispatcher
    withContext(Dispatchers.IO) {
        File(fileName).writeBytes(data)
    }
}

suspend fun updateUI(result: Result) {
    // UI operations should use Main dispatcher
    withContext(Dispatchers.Main) {
        binding.resultText.text = result.message
    }
}
```

### 3. Handle Exceptions Appropriately

```kotlin
suspend fun loadUserData(userId: String): UserData {
    // Domain-level exception handling
    return try {
        // Let repository handle its own technical exceptions
        userRepository.getUser(userId)
    } catch (e: UserNotFoundException) {
        // Handle domain-specific exceptions here
        handleMissingUser(userId)
        throw UserDataException("User not found", e)
    } catch (e: Exception) {
        // Log and translate exceptions for the UI layer
        Log.e("UserLoader", "Failed to load user", e)
        throw UserDataException("Could not load user data", e)
    }
}
```

### 4. Make Expensive Operations Cancellable

```kotlin
suspend fun processLargeDataset(dataset: List<DataItem>): ProcessedResult {
    val results = mutableListOf<ItemResult>()
    
    for (item in dataset) {
        // Check if coroutine is still active
        ensureActive()
        
        // Process item
        val result = processItem(item)
        results.add(result)
        
        // For long operations, periodically yield
        if (results.size % 100 == 0) {
            yield()
        }
    }
    
    return ProcessedResult(results)
}
```

### 5. Use Structured Concurrency

```kotlin
suspend fun loadFullUserProfile(userId: String): UserProfile = coroutineScope {
    // Using structured concurrency ensures all operations complete or fail together
    val basicInfo = async { userRepository.getBasicInfo(userId) }
    val preferences = async { userRepository.getPreferences(userId) }
    val activity = async { activityRepository.getRecentActivity(userId) }
    
    UserProfile(
        info = basicInfo.await(),
        preferences = preferences.await(),
        recentActivity = activity.await()
    )
}
```

### 6. Testing Suspend Functions

```kotlin
class UserRepositoryTest {
    
    @Test
    fun `fetchUser returns user when API call succeeds`() = runTest {
        // Given
        val mockApi = mock<UserApi> {
            onBlocking { getUser("123") } doReturn UserDto("123", "Test User")
        }
        val repository = UserRepository(mockApi)
        
        // When
        val result = repository.fetchUser("123")
        
        // Then
        assertEquals("123", result.id)
        assertEquals("Test User", result.name)
    }
    
    @Test
    fun `fetchUser handles network errors`() = runTest {
        // Given
        val mockApi = mock<UserApi> {
            onBlocking { getUser("123") } doThrow IOException("Network error")
        }
        val repository = UserRepository(mockApi)
        
        // When/Then
        assertFailsWith<NetworkException> {
            repository.fetchUser("123")
        }
    }
}
```

## Conclusion

Suspend functions are a powerful feature of Kotlin that dramatically simplify asynchronous
programming. They allow you to write sequential code that performs asynchronous operations without
blocking threads, improving both code readability and application performance.

Key takeaways:

1. Suspend functions pause execution without blocking threads
2. They work seamlessly with coroutines for structured concurrency
3. Use appropriate dispatchers for different types of work
4. Compose suspend functions to build higher-level abstractions
5. Ensure proper error handling and cancellation support

By understanding and applying these concepts, you can create maintainable, efficient, and robust
asynchronous code in Kotlin.