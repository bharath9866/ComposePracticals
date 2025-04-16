### Kotlin Special Features Interview Questions

#### 1. What are Kotlin extension functions and how do they work?

**Answer:** Extension functions in Kotlin allow you to add new functions to existing classes without modifying their source code. They are defined outside the class but can be called as if they were methods of that class.

Key characteristics:
* Defined using the syntax `fun ReceiverType.functionName(params) { body }`
* Can access public members of the receiver object using `this`
* Cannot access private or protected members of the receiver class
* Resolved statically (not polymorphic)

Example:
```kotlin
fun String.addExclamation(): String {
    return this + "!"
}

// Usage
val result = "Hello".addExclamation() // Returns "Hello!"
```

#### 2. What are the limitations of extension functions?

**Answer:** While extension functions are powerful, they have several limitations:

* Cannot override existing methods of the class
* Cannot access private or protected members of the class
* Are statically resolved (based on the declared type, not the runtime type)
* Cannot be called from Java code in the same way (they're compiled as static methods)
* Cannot modify the class structure (add properties with backing fields)
* Extension properties cannot have backing fields

#### 3. What are inline functions in Kotlin and when should they be used?

**Answer:** Inline functions instruct the compiler to insert the function's bytecode directly at the call site rather than generating a function call.

Key aspects:
* Declared using the `inline` modifier
* Eliminate the overhead of function calls
* Especially useful with higher-order functions to avoid lambda runtime overhead
* Can lead to larger bytecode if the function is called from many places

Example:
```kotlin
inline fun measureTimeMillis(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}
```

Use inline functions when:
* Working with higher-order functions to avoid lambda allocation overhead
* The function body is small
* You need to use non-local returns from lambdas

#### 4. What is the `reified` type parameter in Kotlin and why is it useful?

**Answer:** `reified` is a modifier for generic type parameters in inline functions that allows you to access the actual type information at runtime, which is normally erased due to type erasure in the JVM.

Key points:
* Can only be used with inline functions
* Allows checking and using type information at runtime
* Eliminates the need for explicit Class objects as parameters

Example:
```kotlin
inline fun <reified T> Any.isOfType(): Boolean {
    return this is T
}

// Usage
"string".isOfType<String>() // Returns true
123.isOfType<String>() // Returns false
```

Without `reified`, you would need to pass an explicit Class object:
```kotlin
fun <T> Any.isOfType(clazz: Class<T>): Boolean {
    return clazz.isInstance(this)
}

// Usage
"string".isOfType(String::class.java) // More verbose
```

#### 5. How do infix functions work in Kotlin?

**Answer:** Infix functions allow you to call a function using infix notation (without dots and parentheses), making code more readable and DSL-like.

Requirements for infix functions:
* Must be member functions or extension functions
* Must have a single parameter
* Must be marked with the `infix` modifier

Example:
```kotlin
infix fun Int.powerOf(exponent: Int): Int {
    return Math.pow(this.toDouble(), exponent.toDouble()).toInt()
}

// Usage
val result = 2 powerOf 3 // Same as 2.powerOf(3), returns 8
```

Infix functions are commonly used in:
* DSLs (Domain Specific Languages)
* Collection operations (`to`, `until`, `downTo`)
* Natural language-like expressions

#### 6. Compare and contrast extension functions and regular member functions.

**Answer:** 

Extension Functions:
* Defined outside the class
* Cannot access private/protected members
* Resolved statically (based on the declared type)
* Cannot be overridden in the traditional sense
* Great for extending existing classes without modifying their source
* Cannot be called using polymorphic behavior

Member Functions:
* Defined inside the class
* Can access all class members (private/protected)
* Resolved dynamically (based on the runtime type)
* Can be overridden in subclasses
* Part of the class API
* Support polymorphic behavior

Example showing the difference:
```kotlin
open class Base
class Derived : Base()

fun Base.extFunc() = "Base extension"
fun Derived.extFunc() = "Derived extension"

fun testExtensionFunction() {
    val base: Base = Derived()
    println(base.extFunc()) // Prints "Base extension" (resolved statically)
}
```

#### 7. What is the relationship between inline functions and lambdas in Kotlin?

**Answer:** Inline functions with lambda parameters have a special relationship:

* When a function with lambda parameters is marked as `inline`, the lambda is also inlined
* Eliminates lambda object creation and virtual function call overhead
* Enables non-local returns from lambdas with the `return` keyword
* Lambda parameters can be marked with `noinline` to prevent their inlining
* Makes higher-order functions almost as efficient as regular functions

Example of non-local return (only possible with inline functions):
```kotlin
inline fun processNumbers(numbers: List<Int>, action: (Int) -> Unit) {
    for (number in numbers) {
        action(number)
    }
}

fun findFirstEven(numbers: List<Int>): Int? {
    var result: Int? = null
    
    processNumbers(numbers) { num ->
        if (num % 2 == 0) {
            result = num
            return@processNumbers // With inline, could use `return` directly
        }
    }
    
    return result
}
```

#### 8. How can you use reified type parameters for type checking and casting?

**Answer:** Reified type parameters allow runtime type operations that are typically not possible with regular generics:

```kotlin
// Type checking
inline fun <reified T> Any.isA(): Boolean = this is T

// Type casting
inline fun <reified T> Any.safeCastAs(): T? = this as? T

// Creating instances
inline fun <reified T> createInstance(): T = T::class.java.newInstance()

// Getting class reference
inline fun <reified T> getClassOf(): KClass<T> = T::class

// Usage
val str = "Hello"
str.isA<String>() // true
str.isA<Int>() // false
str.safeCastAs<String>() // Returns "Hello"
str.safeCastAs<Int>() // Returns null
```

Without reified types, you would need to pass class references explicitly as parameters.

#### 9. How would you implement a simple infix function for a custom data type?

**Answer:** Let's implement an infix function for a custom `Point` class:

```kotlin
data class Point(val x: Int, val y: Int) {
    // Infix function to add two points
    infix fun add(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y)
    }
    
    // Infix function to multiply coordinates by a scalar
    infix fun scale(factor: Int): Point {
        return Point(x * factor, y * factor)
    }
}

// Usage
val p1 = Point(1, 2)
val p2 = Point(3, 4)

val sum = p1 add p2 // Point(4, 6)
val scaled = p1 scale 3 // Point(3, 6)

// Chaining infix functions (processed left to right)
val result = p1 add p2 scale 2 // Point(8, 12)
```

Infix functions make the code more readable for certain operations, especially when creating DSLs or working with mathematical constructs.

#### 10. What are the performance implications of using inline functions?

**Answer:** Inline functions have several performance implications:

Advantages:
* Eliminate function call overhead (stack frame creation, etc.)
* Avoid boxing of lambda parameters and functional interfaces
* Reduce memory allocations for lambda objects
* Can improve performance for small, frequently called functions

Potential drawbacks:
* Increase bytecode size when inlining large functions at multiple call sites
* Can negatively impact instruction cache if overused
* May lead to longer compilation times
* Not beneficial for large functions called from many places

Best practices:
* Use `inline` for small functions that take lambda parameters
* Use for performance-critical code where the function call overhead matters
* Consider using `crossinline` when passing lambdas to other execution contexts
* Avoid inlining large functions called from many places
* Use inlining judiciously, focusing on higher-order functions