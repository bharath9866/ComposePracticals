## Kotlin Features Not in Java

## Interview Question: Features in Kotlin but Not in Java

### Question:
Name some of the features that are present in Kotlin but not in Java.

### Answer:
Kotlin introduces several modern features that are not available in Java. Some of these features include:

### 1. Null Safety
Kotlin provides built-in null safety, reducing the chances of `NullPointerException`.

#### Example:
```kotlin
var name: String? = "Kotlin"
name = null  // Allowed because of `?`

val length = name?.length ?: 0  // Safe call operator with Elvis operator
println(length) // Output: 0
```

### 2. Operator Overloading
Kotlin allows operators like `+`, `-`, and `*` to be overloaded for custom types.

#### Example:
```kotlin
class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

val p1 = Point(2, 3)
val p2 = Point(4, 5)
val p3 = p1 + p2  // Uses overloaded `+` operator
println("(${p3.x}, ${p3.y})")  // Output: (6, 8)
```

### 3. Coroutines
Kotlin has native support for coroutines, making asynchronous programming more efficient.

#### Example:
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}
```
*Output:*
```
Hello
World!
```

### 4. Range Expressions
Kotlin allows the use of range expressions like `1..10` for iteration.

#### Example:
```kotlin
for (i in 1..5) {
    println(i)  // Prints numbers from 1 to 5
}

if (3 in 1..5) {
    println("3 is within range")
}
```

### 5. Smart Casts
The compiler automatically handles type casting when it is safe to do so.

#### Example:
```kotlin
fun printLength(obj: Any) {
    if (obj is String) {
        println(obj.length)  // Smart cast to String
    }
}

printLength("Kotlin")  // Output: 6
```

These features make Kotlin a more concise and safer language compared to Java.


## 2. Difference Between `var` and `val` in Kotlin

### Question:
What is the difference between `var` and `val` in Kotlin?

### Answer:
In Kotlin, both `var` and `val` are used for variable declaration, but they have distinct differences:

- `var` (mutable): A variable declared with `var` can be reassigned multiple times.
- `val` (immutable): A variable declared with `val` is read-only and can be assigned only once after initialization.

#### Example:
```kotlin
var name = "John"  // Mutable variable
name = "Doe"        // Allowed

val age = 25       // Immutable variable
age = 30           // Compilation error
```

### Key Differences:
| Feature  | `var` (Variable) | `val` (Value) |
|----------|----------------|---------------|
| Mutability | Can be reassigned | Cannot be reassigned |
| Usage | When a value needs to change | When a value remains constant |
| Behavior | Mutable variable | Read-only variable |

Using `val` whenever possible is recommended, as it helps ensure immutability and safer code.

## 3. What is `inline` function in kotlin?

###Answer:
`Inline` function instruct compiler to insert the complete body of the function wherever that function gets used in the code.

### Advantage of `Inline` function: Function call overhead doesn't occur. Less overhead and faster program execution.

### So, when to make the function `inline` and when not:

• When the function code is very small, it's a good idea to make the function `inline`.

• When the function code is large and called from so many places, it's a bad idea to make the function `inline`, as the large code will be repeated again and again.