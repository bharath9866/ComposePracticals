### Sealed and Enum Classes Interview Questions

#### 1. What are enum classes in Kotlin?

**Answer:** Enum classes in Kotlin are a special type used to represent a fixed set of constants. Each enum constant is an object that can have properties and methods.

```kotlin
enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

// Enum with properties and methods
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF);
    
    fun containsRed(): Boolean {
        return this.rgb and 0xFF0000 != 0
    }
}

// Usage
val direction = Direction.NORTH
val redColor = Color.RED
println(redColor.containsRed()) // true
```

Key characteristics:
* Constants are singletons
* Can implement interfaces
* Can have properties and methods
* Cannot extend other classes
* Cannot have subtypes
* Can be compared with `==`
* Can be used in `when` expressions without requiring an `else` branch

#### 2. What are sealed classes in Kotlin?

**Answer:** Sealed classes in Kotlin are abstract classes that restrict class hierarchies. A sealed class can have multiple subtypes, but all direct subtypes must be declared in the same file as the sealed class.

```kotlin
sealed class Result {
    data class Success(val data: Any) : Result()
    data class Error(val message: String, val exception: Exception? = null) : Result()
    object Loading : Result()
}

// Usage
fun handleResult(result: Result) = when (result) {
    is Result.Success -> displayData(result.data)
    is Result.Error -> showError(result.message)
    Result.Loading -> showLoadingIndicator()
    // No else branch needed
}
```

Key characteristics:
* Abstract by default (cannot be instantiated directly)
* All direct subclasses must be defined in the same file
* Subclasses can be of different types (data class, object, regular class)
* Can have abstract members
* Enables exhaustive `when` expressions without an `else` branch
* Can have multiple instances of each subtype

#### 3. What is the main difference between sealed classes and enum classes in Kotlin?

**Answer:** The main differences between sealed classes and enum classes are:

**Enum classes:**
* Constants are singletons (only one instance of each value)
* All constants are of the same type with the same properties and methods
* Cannot hold different types of data for different constants
* Constants are instantiated once at compile time

**Sealed classes:**
* Can have multiple instances of each subtype
* Subtypes can have different properties and methods
* Can represent different data structures for different subtypes
* Instances are created at runtime

Example illustrating the difference:
```kotlin
// Enum approach - limited, all constants have the same structure
enum class Shape {
    CIRCLE, SQUARE, TRIANGLE;
    
    // All shapes must have the same properties
    fun calculateArea(): Double {
        return when (this) {
            CIRCLE -> Math.PI * 5 * 5  // Hardcoded radius
            SQUARE -> 4.0 * 4.0        // Hardcoded side
            TRIANGLE -> 0.5 * 3 * 4    // Hardcoded base and height
        }
    }
}

// Sealed class approach - flexible, different subtypes with different properties
sealed class BetterShape {
    abstract fun calculateArea(): Double
    
    data class Circle(val radius: Double) : BetterShape() {
        override fun calculateArea(): Double = Math.PI * radius * radius
    }
    
    data class Square(val side: Double) : BetterShape() {
        override fun calculateArea(): Double = side * side
    }
    
    data class Triangle(val base: Double, val height: Double) : BetterShape() {
        override fun calculateArea(): Double = 0.5 * base * height
    }
}

// Usage
val circle = BetterShape.Circle(5.0)
val square = BetterShape.Square(4.0)
```

#### 4. When would you use an enum class over a sealed class?

**Answer:** Use an enum class when:

* You need a fixed set of constants
* All constants have the same type and behavior
* You need to represent a simple set of values (days of week, directions, etc.)
* You need to easily convert to/from string representations
* You need to iterate through all values or count them
* Performance is critical (enums are more memory-efficient than sealed classes)

Example:
```kotlin
enum class Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    
    val isWeekend: Boolean
        get() = this == SATURDAY || this == SUNDAY
}

// Useful enum features
val allDays = Day.values()  // Get all values
val dayName = Day.valueOf("MONDAY")  // Convert string to enum
val dayOrdinal = Day.FRIDAY.ordinal  // Get position (4)
```

#### 5. When would you use a sealed class over an enum class?

**Answer:** Use a sealed class when:

* You need different subtypes with different properties and behaviors
* You need to represent a complex hierarchy
* You need to associate different data with different subtypes
* You need to create multiple instances of the same subtype with different values
* You need to extend existing classes or implement interfaces differently for each subtype

Example:
```kotlin
sealed class ApiResponse<out T> {
    data class Success<T>(val data: T, val metadata: Map<String, Any> = emptyMap()) : ApiResponse<T>()
    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()
    data class Loading(val progress: Float) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}

// Usage with different data types
val userResponse: ApiResponse<User> = ApiResponse.Success(User("Alice"))
val postResponse: ApiResponse<List<Post>> = ApiResponse.Success(listOf(Post("Hello")))

// Multiple instances with different values
val error404 = ApiResponse.Error(404, "Not Found")
val error500 = ApiResponse.Error(500, "Server Error")
```

#### 6. How do sealed classes and enum classes behave differently in a when expression?

**Answer:** Both sealed classes and enum classes allow for exhaustive checking in `when` expressions without requiring an `else` branch, but they behave differently:

```kotlin
// Enum example
enum class Status { ACTIVE, INACTIVE, PENDING }

fun handleStatus(status: Status) = when (status) {
    Status.ACTIVE -> "User is active"
    Status.INACTIVE -> "User is inactive"
    Status.PENDING -> "User is pending"
    // No else branch needed, compiler knows all possible values
}

// Sealed class example
sealed class NetworkState {
    object Idle : NetworkState()
    object Loading : NetworkState()
    data class Success(val data: String) : NetworkState()
    data class Failed(val error: Exception) : NetworkState()
}

fun handleNetworkState(state: NetworkState) = when (state) {
    NetworkState.Idle -> "Waiting for request"
    NetworkState.Loading -> "Loading data"
    is NetworkState.Success -> "Data loaded: ${state.data}"
    is NetworkState.Failed -> "Error: ${state.error.message}"
    // No else branch needed, compiler knows all possible subtypes
}
```

Key differences:
* For enums, you use equality (`==`) checks with the enum constants
* For sealed classes, you typically use type checks (`is`) for different subtypes
* With sealed classes, you can destructure properties in the `when` branch

#### 7. How can you add functionality to enum constants in Kotlin?

**Answer:** Kotlin enums can implement interfaces and define methods that can be overridden by individual enum constants:

```kotlin
interface Describable {
    fun describe(): String
}

enum class PaymentMethod : Describable {
    CREDIT_CARD {
        override fun describe(): String = "Payment with credit card"
        override fun calculateFee(amount: Double): Double = amount * 0.03
    },
    
    DEBIT_CARD {
        override fun describe(): String = "Payment with debit card"
        override fun calculateFee(amount: Double): Double = amount * 0.01
    },
    
    BANK_TRANSFER {
        override fun describe(): String = "Payment with bank transfer"
        override fun calculateFee(amount: Double): Double = 5.0
    },
    
    CASH {
        override fun describe(): String = "Payment with cash"
        override fun calculateFee(amount: Double): Double = 0.0
    };
    
    abstract fun calculateFee(amount: Double): Double
}

// Usage
val method = PaymentMethod.CREDIT_CARD
println(method.describe())          // "Payment with credit card"
println(method.calculateFee(100.0)) // 3.0
```

#### 8. Can sealed classes and enum classes be serialized? How do they differ in this aspect?

**Answer:** Both enum classes and sealed classes can be serialized, but they differ in how they're typically serialized:

**Enum Classes:**
* Easily serializable by default in most libraries
* Usually serialized as their name (string) or ordinal (number)
* Consistent across different platforms

```kotlin
enum class UserRole { ADMIN, USER, GUEST }

// Serialization (conceptual)
val role = UserRole.ADMIN
val serialized = role.name // "ADMIN"
val deserialized = UserRole.valueOf(serialized) // UserRole.ADMIN
```

**Sealed Classes:**
* Require more configuration for serialization
* Need to serialize the class type and its properties
* Often need custom serializers/deserializers

```kotlin
@Serializable
sealed class Message {
    @Serializable
    data class Text(val content: String) : Message()
    
    @Serializable
    data class Image(val url: String, val caption: String? = null) : Message()
    
    @Serializable
    object Typing : Message()
}

// With libraries like kotlinx.serialization, you need to register all subtypes
val format = Json { 
    serializersModule = SerializersModule {
        polymorphic(Message::class) {
            subclass(Message.Text::class)
            subclass(Message.Image::class)
            subclass(Message.Typing::class)
        }
    }
}
```

#### 9. How do you convert between enum values and strings/integers?

**Answer:** Kotlin provides several ways to convert between enum values and strings/integers:

```kotlin
enum class Priority { LOW, MEDIUM, HIGH }

// String conversions
val high = Priority.HIGH
val highName = high.name               // "HIGH"
val highFromString = Priority.valueOf("HIGH")  // Priority.HIGH

// Integer conversions
val medium = Priority.MEDIUM
val mediumOrdinal = medium.ordinal     // 1 (second element, 0-indexed)
val mediumFromOrdinal = Priority.values()[1]  // Priority.MEDIUM

// Custom mapping
enum class HttpStatus(val code: Int) {
    OK(200),
    NOT_FOUND(404),
    SERVER_ERROR(500);
    
    companion object {
        private val map = values().associateBy(HttpStatus::code)
        fun fromCode(code: Int) = map[code] ?: throw IllegalArgumentException("No status for code $code")
    }
}

val status = HttpStatus.fromCode(404)  // HttpStatus.NOT_FOUND
```

Sealed classes don't have built-in name/ordinal properties like enums, so you need to implement custom conversion methods if needed.

#### 10. What happens when you add a new constant to an enum class versus adding a new subclass to a sealed class?

**Answer:** Adding new values to enums and sealed classes has different impacts:

**Adding a new enum constant:**
* Requires recompiling all code that uses the enum
* Forces you to update all exhaustive `when` expressions using the enum
* Can break serialized data if the ordinal values are used

```kotlin
// Before
enum class ConnectionStatus { CONNECTED, DISCONNECTED }

// After adding a constant
enum class ConnectionStatus { CONNECTED, DISCONNECTED, CONNECTING }

// This code now needs an extra case:
fun handleStatus(status: ConnectionStatus) = when (status) {
    ConnectionStatus.CONNECTED -> "Online"
    ConnectionStatus.DISCONNECTED -> "Offline"
    // Must add: ConnectionStatus.CONNECTING -> "Connecting..."
}
```

**Adding a new sealed class subtype:**
* Requires recompiling all code that uses the sealed class
* Forces you to update all exhaustive `when` expressions using the sealed class
* Doesn't affect existing instances of other subtypes

```kotlin
// Before
sealed class UIState {
    object Loading : UIState()
    data class Content(val data: Any) : UIState()
    data class Error(val message: String) : UIState()
}

// After adding a subtype
sealed class UIState {
    object Loading : UIState()
    data class Content(val data: Any) : UIState()
    data class Error(val message: String) : UIState()
    object Empty : UIState() // New subtype
}

// The when expression must be updated:
fun render(state: UIState) = when (state) {
    is UIState.Loading -> showSpinner()
    is UIState.Content -> displayContent(state.data)
    is UIState.Error -> showError(state.message)
    // Must add: UIState.Empty -> showEmptyState()
}
```

In both cases, the Kotlin compiler helps you identify all places that need updating through exhaustive `when` checks, which is a key benefit of both enum and sealed classes.

#### 11. Can you nest sealed classes and enum classes? How do they differ?

**Answer:** Both sealed classes and enum classes can be nested, but with different capabilities:

**Nested Enum Classes:**
* Can be declared inside classes or objects
* Access to the outer class's members

```kotlin
class PaymentProcessor {
    enum class Status {
        PENDING, PROCESSING, COMPLETED, FAILED
    }
    
    fun processPayment(): Status {
        // Implementation
        return Status.COMPLETED
    }
}

// Usage
val status = PaymentProcessor.Status.PENDING
```

**Nested Sealed Classes:**
* Can be declared inside classes or objects
* Can have their subtypes nested further
* Can create complex hierarchies

```kotlin
class UiComponent {
    sealed class Event {
        sealed class Touch : Event() {
            data class Tap(val x: Int, val y: Int) : Touch()
            data class LongPress(val x: Int, val y: Int, val duration: Long) : Touch()
            data class Swipe(val startX: Int, val startY: Int, val endX: Int, val endY: Int) : Touch()
        }
        
        sealed class Keyboard : Event() {
            data class KeyPress(val keyCode: Int) : Keyboard()
            object EnterPressed : Keyboard()
            object BackspacePressed : Keyboard()
        }
        
        sealed class Lifecycle : Event() {
            object Created : Lifecycle()
            object Destroyed : Lifecycle()
        }
    }
    
    fun handleEvent(event: Event) {
        when (event) {
            is Event.Touch.Tap -> handleTap(event)
            is Event.Touch.LongPress -> handleLongPress(event)
            is Event.Touch.Swipe -> handleSwipe(event)
            is Event.Keyboard.KeyPress -> handleKeyPress(event)
            Event.Keyboard.EnterPressed -> handleEnter()
            Event.Keyboard.BackspacePressed -> handleBackspace()
            Event.Lifecycle.Created -> initialize()
            Event.Lifecycle.Destroyed -> cleanup()
        }
    }
}
```

A key difference is that sealed classes allow for more complex hierarchies with multiple levels of inheritance.

#### 12. How do you implement the Singleton pattern in Kotlin, and how does it relate to enum and sealed classes?

**Answer:** There are several ways to implement the Singleton pattern in Kotlin:

**Using object declarations (recommended):**
```kotlin
object DatabaseConnection {
    fun connect() = println("Connected to database")
}
```

**Using a sealed class with an object:**
```kotlin
sealed class Logger {
    object Instance : Logger() {
        fun log(message: String) = println("LOG: $message")
    }
}

// Usage
Logger.Instance.log("Hello")
```

**Using an enum with a single instance (less common):**
```kotlin
enum class Singleton {
    INSTANCE;
    
    fun doSomething() = println("Singleton action")
}

// Usage
Singleton.INSTANCE.doSomething()
```

How they relate:
* Kotlin's `object` declaration is the most direct way to create a singleton
* Enum constants are guaranteed to be singletons by the language
* Sealed classes can contain `object` declarations as subtypes, which are singletons
* Using an enum for a singleton is uncommon but provides the same singleton guarantee as an `object`

The enum approach was a common pattern in Java before Kotlin's `object` declaration was available, but is less idiomatic in Kotlin.