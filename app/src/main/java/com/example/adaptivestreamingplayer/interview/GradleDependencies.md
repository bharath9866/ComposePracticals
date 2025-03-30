### 5. (Gradle) What is the difference between implementation and api dependencies?

When adding dependencies in Gradle, understanding the difference between `implementation` and `api` configurations is crucial for optimizing build times and managing dependency visibility. Here's what you need to know:

#### Implementation Dependencies

The `implementation` dependency configuration restricts the scope of dependencies to the module where they are declared. This means that:

- The dependency is only accessible to the module where it is declared
- If module A depends on module B, and B uses `implementation` for its dependencies, those dependencies are not visible to module A
- Changes in an `implementation` dependency do not trigger recompilation of dependent modules
- This results in faster build times since changes to the implementation details of a module don't affect modules that depend on it

```kotlin
dependencies {
    implementation("com.example:logging-library:1.0.0")
}
```

#### API Dependencies

The `api` dependency configuration exposes dependencies to modules that depend on the module where the dependency is declared. This means that:

- The dependency is accessible to the module where it is declared AND to all modules that depend on it
- If module A depends on module B, and B uses `api` for its dependencies, those dependencies are visible to module A
- Changes in an `api` dependency may trigger recompilation of all dependent modules
- This typically results in slower build times compared to `implementation`, as changes can cascade through the dependency chain

```kotlin
dependencies {
    api("com.example:logging-library:1.0.0")
}
```

#### Example Scenario

Consider a project with two modules: `ui` and `core`. The `ui` module depends on the `core` module, and the `core` module depends on a `logging-library`.

**If `logging-library` is declared as `implementation` in `core`**:
- `ui` → `core` → implementation(`logging-library`)
- The `ui` module cannot access the `logging-library` directly
- Changes in `logging-library` only require recompilation of the `core` module
- Faster builds, as changes are isolated

**If `logging-library` is declared as `api` in `core`**:
- `ui` → `core` → api(`logging-library`)
- The `ui` module can access and use the `logging-library` directly
- Changes in `logging-library` may require recompilation of both `core` and `ui` modules
- Slower builds, as changes propagate to all dependent modules

#### When to Use Each

- Use `implementation` when the dependency is an implementation detail that shouldn't be exposed to other modules (default choice for most dependencies)
- Use `api` when the dependency is part of the public API of your module and needs to be accessible to modules that depend on it

By choosing the appropriate dependency configuration, you can optimize build times and create cleaner module boundaries in your project.