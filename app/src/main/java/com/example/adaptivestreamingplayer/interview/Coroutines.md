### 1. (Kotlin) Corotuines vs. Thread

The difference between **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** and **Threads** in Android (and Kotlin in general) lies in how they manage concurrency, resource consumption, and performance.

#### 1. **Lightweight vs. Heavyweight**
- **Coroutines** are **lightweight**. They run within a single thread but can be suspended without blocking the thread. This allows thousands of coroutines to run concurrently on fewer threads with minimal overhead.
- **Threads**, on the other hand, are **heavyweight**. Each thread has its own memory and resources, and switching between threads involves more overhead, leading to higher resource consumption when dealing with many threads.

#### 2. **Concurrency vs. Parallelism**
- **Coroutines** offer **concurrency** by allowing multiple tasks to be suspended and resumed without occupying a separate thread. They do not necessarily run tasks in parallel but allow cooperative multitasking.
- **Threads** offer **parallelism** by running tasks simultaneously on multiple cores. Each thread can perform tasks independently, which can be useful for CPU-bound operations.

#### 3. **Thread Blocking vs. Suspension**
- **Threads** perform **blocking** operations. If a thread is waiting for an I/O operation or sleep call, it will not be able to perform other tasks including coroutines which are inside of that thread.
- **Suspension** Points are any Instructions that Require Waiting for a Specific Outcome.
- **Coroutines** use a **suspension** mechanism, meaning they do not block a thread while waiting for a task to complete. When a coroutine is suspended (e.g., while waiting for a network response), the underlying thread can execute other coroutines.
- Advantages of using Coroutines:
1. More Lightweight than Threads
2. Easy to Work with
   - Example of Callback vs Coroutine Based :
        1. Callback-Based:
            ```kotlin
                   fun main() {
                          println("Requesting data...")
                          fetchData { response ->
                              println("Response: $response")
                          }
                   }
                        
                   fun fetchData(callback: (String) -> Unit) {
                          Thread.sleep(1000)
                          callback("Data")
                   }
              ```
        2. Coroutine-Based:
            ```Kotlin
              import kotlinx.coroutines.*
        
              fun main() = runBlocking {
                  println("Requesting Data...")
                  val response = fetchData()
                  println("Response: $response")
              }
        
              suspend fun fetchData() :String {
                  delay(1000)
                  return "Data"
            }
            ```
3. Easy Switching Between Threads
   - Example: Switching between from IO Thread to Main Thread using like a **withContext(Dispatcher.Main) { }**


#### 4. **Efficiency**
- **Coroutines** are **more efficient** in terms of memory and CPU usage because they avoid context switching between threads and use fewer system resources.
- **Threads** consume more resources due to the overhead of thread creation, scheduling, and context switching between threads.

#### 5. **Context Switching**
- **Coroutines** allow switching between tasks using **suspension points** (like `delay()` or `withContext()`), which is less expensive than switching between threads.
- **Threads** involve **context switching** handled by the operating system, which can be more costly in terms of performance.

#### 6. **Use Cases**
- **Coroutines** are ideal for **I/O-bound tasks**, like making network requests, handling database operations, and UI updates.
- **Threads** are better suited for **CPU-bound tasks**, where actual parallel computation (e.g., intensive image processing, large computations) may be needed.

#### 7. **Error Handling**
- **Coroutines** provide structured concurrency APIs like `Job`, `CoroutineExceptionHandler` to handle exceptions and cancel tasks easily, and coroutine builder, such as `launch` and `async`, which immediately propagates exceptions.
- **Threads** require more manual error handling (try-catch or `uncaughtExceptionHandler`) and coordination for task cancellation and exception propagation.

#### Summary

**Coroutines** are more suitable for managing large numbers of tasks concurrently with minimal overhead, while **Threads** are better for parallel execution when multiple CPU cores are required.

### 2. Structured Concurrency in Kotlin Coroutines:
* Structured concurrency is a programming paradigm that provides a way to manage and organize concurrent code execution in a controlled manner.
* Kotlin Coroutines has followed this paradigm while designing by including the following:
    • Job: Control over the current status of the job.
    • Scope: Control to cancel all the jobs when going out of the scope.
    • Error Propagation: Depending on our use case, we can control the action when the error propagates to the parent.

* For example: If one of the child's tasks fails, the error gets propagated to the parent, the parent can cancel all the other child's tasks, or the parent can continue with all the other child's tasks. It is under our control.

### 3. CoroutineContext in Kotlin:
* CoroutineContext is a key interface in Kotlin's coroutine framework that defines the environment and behavior in which a coroutine executes.
1. It functions as an indexed set of elements, where each element has a unique key and contributes specific properties to the execution context. 
2. This interface essentially acts as a map-like collection of various context elements that determine crucial aspects of coroutine behavior such as threading policy, error handling, and lifecycle management.

3. **Core Elements**:
    - **Job**: Controls the lifecycle of the coroutine, including it's cancellation and completion states.
    - **CoroutineDispatcher**: Determines which thread or threads the coroutine runs on
    - **CoroutineName**: Assigns a name to the coroutine (useful for debugging)
    - **CoroutineExceptionHandler**: Handles uncaught exceptions

4. **Inheritance**: Coroutines inherit the context from their parent coroutine scope. This supports the structured concurrency model.
#### CoroutineDispatchers

Dispatchers determine on which thread pool coroutines will be executed:

- **Dispatchers.Main**: For UI operations on Android's main thread
- **Dispatchers.IO**: Optimized for disk and network I/O operations
- **Dispatchers.Default**: For CPU-intensive operations
- **Dispatchers.Unconfined**: Runs coroutine in the current thread until the first suspension point
#### Context Operators

1. **Addition (`+` operator)**: Combines two contexts, with elements from the right-hand side taking precedence

- If we see the **source code** of the `launch` in Coroutines:
```kotlin
// code changed to make it simple
fun <SomeScope>.launch(context: CoroutineContext = ...) {
    // code removed for brevity
}
```

The first parameter is `CoroutineContext`.

Now, Let's see the source code of the CoroutineContext.

```kotlin
public interface CoroutineContext {

    public operator fun <E : Element> get(key: Key<E>): E?

    // code removed for brevity

    public operator fun plus(context: CoroutineContext): CoroutineContext

    // code removed for brevity
}
```

The source code of the Element.

```kotlin
public interface CoroutineContext {

      // code removed for brevity

  public interface Element : CoroutineContext {

      // code removed for brevity

  }

}
```

By seeing the source code, we can see that CoroutineContext has a set of elements `(CoroutineContext.Element)` and these elements define the behavior of a coroutine.

At the beginning, we learned about those four elements:
. Dispatcher
. Job
. CoroutineName
. CoroutineExceptionHandler

These elements must be the type of `CoroutineContext.Element` internally. Let me show you the source code to understand.

* Job Source Code:
```kotlin
public interface Job : CoroutineContext.Element {
  // code removed for brevity
}
```

`Job` is of type `CoroutineContext.Element`.

Similarly, if we see the source code for Dispatcher, CoroutineName, and CoroutineExceptionHandler, we can see that they are also the type of `CoroutineContext.Element` internally.

That is why, we can create a CoroutineContext using the `plus` (+) operator to define all the elements as below:

```kotlin
val coroutineContext: CoroutineContext =
    Dispatchers.IO +
    Job() +
    CoroutineName("OutcomeSchoolCoroutine") +
    CoroutineExceptionHandler { _, _ -> /* Handle Exception */ }
```

*Note:* I have used GlobalScope for quick examples, we should avoid using it at all costs. In an Android project, we should use custom scopes based on our usecase such as `lifecycleScope`, `viewModelScope` etc.

Now we need to learn how to define and use each of the above-mentioned elements. To do this, we'll explore the customizations that can be easily made within the CoroutineContext.
#### Customization in CoroutineContext
Let's see the **Hello World** of the Coroutine:

```kotlin
GlobalScope.launch {
  // do some work
}
```

In the example mentioned above, the default CoroutineContext will be used since we have not provided a custom CoroutineContext.

As we know, CoroutineContext helps manage the Dispatcher, Job, CoroutineName, and CoroutineExceptionHandler. We can modify one or more of these elements based on our use case.

Initially, we will start by changing only the Dispatcher to gain a basic understanding.

We can write the code as below:
```kotlin
GlobalScope.launch(Dispatchers.IO) {
  // do some work
}
```

Here, we have specified the Dispatcher for the Coroutine to use during task execution. The task will be executed on the IO Dispatcher.

Now, let's suppose we want to change the CoroutineName in addition to the Dispatcher.

We can write the code as below:
```kotlin
GlobalScope.launch(Dispatchers.IO + CoroutineName("OutcomeSchoolCoroutine")) {
  // do some work
}
```

Here, we have used the plus operator.

Now, suppose we want to change other parameters in addition to the Dispatcher and CoroutineName. We can use the + operator again to add the other parameters as shown below:

```kotlin
GlobalScope.launch(
    Dispatchers.IO +
            Job() +
            CoroutineName("OutcomeSchoolCoroutine") +
            CoroutineExceptionHandler { _, _ -> /* Handle Exception */ }
) {
  // do some work
}
```

Or, we can create a CoroutineContext:
```kotlin
val coroutineContext: CoroutineContext =
    Dispatchers.IO +
    Job() +
    CoroutineName("OutcomeSchoolCoroutine") +
    CoroutineExceptionHandler { _, _ -> /* Handle Exception */ }
```

And then use it like below:
```kotlin
GlobalScope.launch(coroutineContext) {
  // do some work
}
```



### 4. Launch vs Async:
* Both launch and async are the functions in Kotlin to start the Coroutines.
  * `launch {}`
  * `async{}`
* The difference is that the `launch{}` returns a `Job` and does not carry any resulting value whereas the `async{}` returns an instance of `Deferred<T>`, which has an `await()` function that returns the result of the coroutine like we have future in Java in which we do `future.get()` to get the result.
* In other words:
  * **launch**: fire and forget.
  * **async**: perform a task and return a result.
* Let's take an example to learn launch vs async.

1. We can use the `launch` as below:
    ```kotlin
    val job = GlobalScope.launch(Dispatchers.Default) {
        // do something and do not return result
    }
    ```
    * It returns a job object which we can use to get a job's status or to cancel it.
    * In the above example of launch, we have to do something and **NOT** return the result back.

2. But when we need the result back, we need to use the `async`:
    ```kotlin
    val deferredJob = GlobalScope.async(Dispatchers.Default) {
        // do something and return result, for example 10 as a result
        return@async 10
    }
    val result = deferredJob.await() // result = 10
    ```
   * Here, we get the result using the `await()`.
   * In async also, we can use the `Deferred` job object to get a job's status or to cancel it.
     * **Note:** I have used GlobalScope for quick examples, we should avoid using it at all costs. In an Android project, we should use custom scopes based on our usecase such as `lifecycleScope`, `viewModelScope` and etc.,
   
   **Another difference between launch and async is in terms of exception handling.**
   * If any exception comes inside the launch block, it crashes the application if we have not handled it. 
   * However, if any exception comes inside the async block, it is stored inside the resulting `Deferred` and is not delivered anywhere else, it will get silently dropped unless we handle it.
   * Let's understand this difference with code examples.
   * Suppose we have a function that does something and throws and exception:
    ```kotlin
    private fun doSomethingAndThrowException() {
        throw Exception("Some Exception")
    } 
    ```
   * Now using it with the **launch**:
    ```kotlin
    GlobalScope.launch {
        doSomethingAndThrowException()
    }
    ```
   * It will **CRASH** the application as expected.
   * We can handle it as below:
    ```kotlin
    GlobalScope.launch {
        try {
            doSomethingAndThrowException()
        } catch (e: Exception) {
            // handle exception
        }
    }
    ```
   * Now, the exception will come inside the catch block and we can handle it.
   * And now, using it with the `async`:
    ```kotlin
    GlobalScope.async {
        doSomethingAndThrowException()
    }
    ```
   * The application will **NOT** crash. The exception will get dropped silently.
   * Again, we can handle it as below:
    ```kotlin
    GlobalScope.async {
        try {
            doSomethingAndThrowException()
        } catch (e: Exception) {
            // handle exception
        }
    }
    ```

    * Now, the exception will come inside the catch block and we can handle it.
    * Let me tabulate the difference between launch and async.

| Aspect               | Launch                                                                 | Async                                                                                                  |
|----------------------|-----------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|
| **Behavior**         | Fire and forget.                                                      | Perform a task and return a result.                                                                    |
| **Return Type**      | `Job` (no resulting value).                                           | `Deferred<T>` (result retrieved via `await()`).                                                        |
| **Exception Handling**| Exceptions crash the app if unhandled.                               | Exceptions are stored in the `Deferred` and get silently dropped unless explicitly handled (e.g., via `await()`). |
