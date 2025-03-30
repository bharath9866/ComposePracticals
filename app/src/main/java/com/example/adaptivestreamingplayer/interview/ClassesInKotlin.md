### 5. Sealed Classes in Kotlin?

A sealed class in Kotlin in used to represent a restricted class hierarchy. It is a special kind of class that allows you to define a set of subclasses within the same file so when using it in a when expression, the compiler can verify if all cases are covered.

#### Restricted class hierarchy:
All subclasses must be defined within the same file or inside the sealed class declaration.

#### Exhaustive when expressions: 
When using a sealed class in a when expression, the compiler can verify if all cases are covered:

```kotlin

sealed class Result {
    // Subclasses must be defined within the sealed class or in the same file
    data class Success(val data:String): Result()
    data class Error(val message:String): Result()
    object Loading: Result()
}

fun handleResult(result:Result) = when(result) {
    is Result.Success -> println("Success: ${result.data}")
    is Result.Error -> println("Error: ${result.message}")
    is Result.Loading -> println("Loading...")
    // No 'else' branch needed - compiler knows all possible subclasses
}
```
#### Abstract by default: 
Sealed classes are abstract by default and cannot be instantiated directly.

#### Example Use Cases:
- Representing state in UI components (loading, success, error).
- Handling API responses.
