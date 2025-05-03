### Dependency Injection Interview Questions

#### 1. What is Dependency Injection and why is it important?

**Answer:** Dependency Injection (DI) is a design pattern that allows us to remove hard-coded dependencies and make our application loosely coupled, more maintainable, and easier to test.

Instead of instantiating dependencies inside a class, they are provided to the class from an external source:

```kotlin
// Without dependency injection
class UserRepository {
    // Hard-coded dependency
    private val database = Database()
    
    fun getUser(id: String): User {
        return database.findUser(id)
    }
}

// With dependency injection
class UserRepository(private val database: Database) {
    fun getUser(id: String): User {
        return database.findUser(id)
    }
}
```

Key benefits:
* Easier unit testing through mocking dependencies
* Loose coupling between classes
* Greater code reusability
* Better maintainability
* Easier to implement design patterns

#### 2. What are the different types of Dependency Injection?

**Answer:** There are three main types of dependency injection:

1. **Constructor Injection**: Dependencies are provided through a class constructor.
```kotlin
class UserService(private val repository: UserRepository)
```

2. **Setter Injection**: Dependencies are provided through setter methods.
```kotlin
class UserService {
    lateinit var repository: UserRepository
    
    fun setRepository(repository: UserRepository) {
        this.repository = repository
    }
}
```

3. **Field Injection**: Dependencies are injected directly into fields.
```kotlin
class UserService {
    @Inject
    lateinit var repository: UserRepository
}
```

Constructor injection is generally preferred as it:
* Ensures all required dependencies are available when the object is created
* Makes dependencies explicit
* Works well with immutable objects
* Makes the class easier to test

#### 3. What is a Dependency Injection Container/Framework?

**Answer:** A DI Container (or DI Framework) is a tool that helps to manage the creation and lifecycle of dependencies. It automatically creates objects and their dependencies and provides them to classes that need them.

Key responsibilities:
* **Object creation**: Creates instances of classes and their dependencies
* **Dependency resolution**: Resolves what implementation to use for each dependency
* **Lifetime management**: Manages the lifecycle of dependencies (singleton, transient, etc.)
* **Injection**: Provides dependencies to classes that need them

Popular DI frameworks:
* **Dagger/Hilt**: Compile-time DI frameworks for Android and Java
* **Koin**: Lightweight DI framework for Kotlin
* **Spring**: Comprehensive DI framework for Java
* **Guice**: Google's DI framework for Java

#### 4. What is the difference between Service Locator and Dependency Injection?

**Answer:** Both patterns help manage dependencies but differ in fundamental ways:

**Dependency Injection**:
* Dependencies are "pushed" into a class from outside
* The class is passive and doesn't know where dependencies come from
* Promotes better testability and less coupling
* The DI framework handles object creation and injection

```kotlin
// Dependency Injection example
class UserPresenter(private val userService: UserService)
```

**Service Locator**:
* Dependencies are "pulled" by a class from a central registry
* The class actively acquires its dependencies
* Creates hidden dependencies through the service locator
* More difficult to test because of the coupling to the service locator

```kotlin
// Service Locator example
class UserPresenter {
    private val userService = ServiceLocator.getUserService()
}
```

DI is generally preferred for larger applications because it results in more maintainable and testable code.

#### 5. What is the difference between Dagger and Hilt?

**Answer:** Dagger and Hilt are both dependency injection frameworks for Android, but Hilt is built on top of Dagger to simplify its use:

**Dagger**:
* General-purpose DI framework
* Requires more boilerplate code
* Provides complete control over the dependency graph
* Steeper learning curve
* Manual setup of components and modules

**Hilt**:
* Android-specific DI framework built on top of Dagger
* Reduces boilerplate through predefined components
* Standardizes the way DI is implemented in Android apps
* Easier to learn and use
* Provides Android-specific scopes and annotations

Example Hilt code:
```kotlin
@HiltAndroidApp
class MyApplication : Application()

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var analytics: AnalyticsService
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAnalyticsService(): AnalyticsService {
        return FirebaseAnalyticsService()
    }
}
```

#### 6. What is the purpose of @Inject, @Module, and @Component annotations in Dagger?

**Answer:** These are key annotations used in Dagger:

**@Inject**:
* Marks constructors, fields, or methods where Dagger should provide dependencies
* When applied to a constructor, tells Dagger how to create instances of the class

```kotlin
class UserRepository @Inject constructor(private val api: UserApi)
```

**@Module**:
* Identifies a class that provides dependencies that can't be constructor-injected
* Used for interface implementations or third-party classes

```kotlin
@Module
class NetworkModule {
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}
```

**@Component**:
* Serves as a bridge between @Inject and @Module
* Defines the connection between providers of objects and their consumers
* Generates the container that manages dependencies

```kotlin
@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    
    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}
```

#### 7. What are scopes in dependency injection?

**Answer:** Scopes control how long an instance of a dependency lives before it's recreated. They help manage the lifecycle of objects and optimize resource usage.

Common scopes:
* **Singleton**: A single instance exists for the entire application lifecycle
* **ApplicationScope**: Similar to singleton, tied to the application's lifecycle
* **ActivityScope**: Instance lives as long as an Activity is alive
* **FragmentScope**: Instance lives as long as a Fragment is alive
* **ViewModelScope**: Instance lives as long as a ViewModel is alive
* **RequestScope**: Instance lives for a single request (in web applications)

Example with Dagger/Hilt:
```kotlin
// Defining a custom scope
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

// Using a scope
@ActivityScope
@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}

// Providing a scoped instance
@Module
class ActivityModule {
    @Provides
    @ActivityScope
    fun provideUserManager(api: UserApi): UserManager {
        return UserManagerImpl(api)
    }
}
```

#### 8. What are the benefits of using dependency injection in unit testing?

**Answer:** Dependency injection significantly improves testability:

* **Isolation**: Tests only the class under test, not its dependencies
* **Mocking**: Easily replace real dependencies with test doubles (mocks, stubs)
* **Predictable behavior**: Control the behavior of dependencies for specific test scenarios
* **Focused tests**: Write tests that verify only the logic in the class being tested
* **Faster tests**: No need to set up complex dependencies like databases or network services

Example:
```kotlin
// Class with dependency injection
class UserViewModel(private val userRepository: UserRepository)

// Test with mocked dependency
class UserViewModelTest {
    @Test
    fun `should show error when repository fails`() {
        // Create mock repository
        val mockRepo = mock(UserRepository::class.java)
        `when`(mockRepo.getUser("123")).thenReturn(Result.Error("Network error"))
        
        // Create the class under test with the mock
        val viewModel = UserViewModel(mockRepo)
        
        // Trigger the functionality
        viewModel.loadUser("123")
        
        // Verify the expected behavior
        assertEquals(UiState.Error("Network error"), viewModel.state.value)
    }
}
```

#### 9. What is the difference between @Binds and @Provides in Dagger?

**Answer:** Both annotations provide dependencies in Dagger modules but are used in different scenarios:

**@Provides**:
* Used in regular methods inside modules
* Allows you to instantiate objects with custom logic
* Required for cases where you need to call a method to create the dependency
* Suitable for third-party libraries or complex instantiation

```kotlin
@Module
class NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
```

**@Binds**:
* Used with abstract methods in abstract modules
* More efficient as it doesn't create a method call at runtime
* Only works for interface-to-implementation binding
* Cannot contain any implementation logic

```kotlin
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
```

Use `@Binds` for simple interface bindings and `@Provides` when you need custom instantiation logic.

#### 10. What is the role of Qualifier annotations in dependency injection?

**Answer:** Qualifiers help distinguish between multiple implementations of the same type. They're used when you need to inject different implementations of the same interface in different places.

Using Dagger as an example:
```kotlin
// Define qualifiers
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OtherInterceptorOkHttpClient

// Provide dependencies with qualifiers
@Module
class NetworkModule {
    @Provides
    @Singleton
    @AuthInterceptorOkHttpClient
    fun provideAuthOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }
    
    @Provides
    @Singleton
    @OtherInterceptorOkHttpClient
    fun provideOtherOkHttpClient(otherInterceptor: OtherInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(otherInterceptor)
            .build()
    }
}

// Inject with qualifiers
class ApiService @Inject constructor(
    @AuthInterceptorOkHttpClient private val authClient: OkHttpClient,
    @OtherInterceptorOkHttpClient private val otherClient: OkHttpClient
) {
    // Use clients as needed
}
```

Without qualifiers, Dagger wouldn't know which OkHttpClient to inject when there are multiple bindings for the same type.

#### 11. How does Koin differ from Dagger as a DI framework?

**Answer:** Koin and Dagger are both popular DI frameworks, but take different approaches:

**Koin**:
* Pure Kotlin, service locator pattern with DI-like syntax
* Runtime dependency resolution (no code generation)
* Lighter, more concise syntax
* No annotation processing, faster build times
* Less compile-time safety but easier to learn
* DSL-based configuration

```kotlin
// Koin module definition
val appModule = module {
    single { DatabaseHelper(androidContext()) }
    factory { UserRepository(get()) }
    viewModel { UserViewModel(get()) }
}

// Start Koin in Application class
startKoin {
    androidContext(this@MyApplication)
    modules(appModule)
}

// Usage
class UserActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModel()
}
```

**Dagger/Hilt**:
* Java/Kotlin with annotation processing
* Compile-time dependency resolution and validation
* More verbose, annotation-based syntax
* Generates code, can slow down builds
* Stronger compile-time guarantees but steeper learning curve
* Component-based architecture

Koin is often preferred for smaller projects or prototypes due to its simplicity, while Dagger/Hilt are better suited for larger projects where compile-time safety is crucial.

#### 12. What are some common pitfalls to avoid when implementing dependency injection?

**Answer:** Common mistakes to avoid when using dependency injection:

1. **Circular dependencies**: When two objects depend on each other, creating a dependency cycle
   ```kotlin
   // Avoid this:
   class A(val b: B)
   class B(val a: A) // Circular dependency!
   ```

2. **Service locator anti-pattern**: Using a DI framework as a glorified service locator
   ```kotlin
   // Avoid this:
   class UserService {
      fun doSomething() {
         val dependency = DaggerComponent.get().provideDependency()
      }
   }
   ```

3. **Over-injection**: Injecting too many dependencies into a class, violating single responsibility
   ```kotlin
   // Consider refactoring:
   class UserManager @Inject constructor(
      private val userApi: UserApi,
      private val userDao: UserDao,
      private val analytics: Analytics,
      private val logger: Logger,
      private val preferences: Preferences,
      private val fileManager: FileManager,
      // Too many dependencies!
   )
   ```

4. **Wrong scoping**: Creating too many instances or keeping instances alive too long
   ```kotlin
   // Incorrectly scoping a heavy object:
   @ActivityScoped // Should be singleton or application scope
   class ExpensiveDatabase @Inject constructor()
   ```

5. **Testing challenges**: Making it difficult to replace dependencies in tests
   ```kotlin
   // Hard to test:
   class UserRepository {
      private val api = DaggerComponent.get().api() // Hard-coded reference
   }
   ```

6. **Too much abstraction**: Over-engineering by creating interfaces for everything
   ```kotlin
   // May be unnecessary:
   interface Logger {
       fun log(message: String)
   }
   
   class LoggerImpl @Inject constructor() : Logger {
       override fun log(message: String) {
           println(message)
       }
   }
   ```

7. **Ignoring object lifecycles**: Not considering the lifecycle implications of injected dependencies
   ```kotlin
   // Dangerous:
   @Singleton
   class UserManager @Inject constructor(
       private val activity: Activity // Activity reference in a singleton!
   )
   ```