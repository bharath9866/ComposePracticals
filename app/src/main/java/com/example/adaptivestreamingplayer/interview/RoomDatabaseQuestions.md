### Room Database Interview Questions

#### 1. What is Room and why use it over direct SQLite implementation?

**Answer:** Room is a persistence library that provides an abstraction layer over SQLite. Benefits include:

* Compile-time verification of SQL queries
* Convenient annotations that reduce boilerplate code
* Easy integration with other Architecture components
* Built-in migration support
* Type safety for database operations
* Direct mapping between SQLite tables and Java/Kotlin objects

#### 2. What are the main components of Room?

**Answer:** Room has three main components:

* **Entity**: Annotated classes that represent database tables
* **DAO (Data Access Object)**: Interfaces that define database operations
* **Database**: Abstract class that serves as the database holder and main access point

Example:
```kotlin
// Entity
@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String
)

// DAO
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>
    
    @Insert
    fun insertAll(vararg users: List<User>)
    
    @Delete
    fun delete(user: User)
}

// Database
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

#### 3. What annotations are commonly used in Room and what do they do?

**Answer:** Common Room annotations include:

* **@Entity**: Marks a class as a database table
* **@PrimaryKey**: Designates the primary key of an entity
* **@ColumnInfo**: Specifies the name of a column in the table
* **@Dao**: Marks an interface as a Data Access Object
* **@Database**: Marks a class as a Room database
* **@Query**: Defines a SQLite query method
* **@Insert**: Marks a method that inserts data
* **@Update**: Marks a method that updates data
* **@Delete**: Marks a method that deletes data
* **@Transaction**: Marks a method that should be executed in a transaction
* **@Relation**: Defines a relationship between entities
* **@Embedded**: Nests a set of columns from another entity
* **@Ignore**: Fields to be ignored by Room
* **@TypeConverter**: Converts between custom types and Room-supported types

#### 4. How do you define relationships between entities in Room?

**Answer:** Room supports several types of relationships:

1. **One-to-One**: Using a reference object with @Relation

```kotlin
data class UserWithProfile {
    @Embedded val user: User
    @Relation(
        parentColumn = "uid",
        entityColumn = "userId"
    )
    val profile: Profile
}
```

2. **One-to-Many**: Similar to one-to-one but returns a list

```kotlin
data class UserWithPosts {
    @Embedded val user: User
    @Relation(
        parentColumn = "uid",
        entityColumn = "userId"
    )
    val posts: List<Post>
}
```

3. **Many-to-Many**: Uses a junction/cross-reference table

```kotlin
@Entity
data class Student(
    @PrimaryKey val studentId: Int,
    val name: String
)

@Entity
data class Course(
    @PrimaryKey val courseId: Int,
    val title: String
)

@Entity(primaryKeys = ["studentId", "courseId"],
        foreignKeys = [
            ForeignKey(entity = Student::class,
                       parentColumns = ["studentId"],
                       childColumns = ["studentId"]),
            ForeignKey(entity = Course::class,
                       parentColumns = ["courseId"],
                       childColumns = ["courseId"])
        ])
data class StudentCourseCrossRef(
    val studentId: Int,
    val courseId: Int
)

data class StudentWithCourses {
    @Embedded val student: Student
    @Relation(
        parentColumn = "studentId",
        entityColumn = "courseId",
        associateBy = Junction(StudentCourseCrossRef::class)
    )
    val courses: List<Course>
}
```

#### 5. How do you handle migrations in Room?

**Answer:** Room migrations are handled by implementing the Migration class:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE users ADD COLUMN age INTEGER")
    }
}

// Then provide the migration when building the database
Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name")
    .addMigrations(MIGRATION_1_2)
    .build()
```

Alternative strategies include:
* `.fallbackToDestructiveMigration()`: Destroys and rebuilds the database (data loss)
* `.fallbackToDestructiveMigrationFrom(1, 3)`: Only destroy when migrating from specific versions
* Pre-packaged databases with `.createFromAsset()` or `.createFromFile()`

#### 6. What is a TypeConverter in Room and when would you use it?

**Answer:** TypeConverters allow Room to work with custom data types that it doesn't natively support. You use them to convert between complex objects and primitive types that Room can store.

```kotlin
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

// Register the converter with the database
@Database(entities = [User::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

Common use cases include:
* Date/Time objects
* Custom enums
* List/Set of primitive types
* JSON objects (using Gson/Moshi/etc.)
* Geographical coordinates
* Color values

#### 7. How can you perform asynchronous operations with Room?

**Answer:** Room doesn't allow database operations on the main thread by default. Options for async operations:

1. **Coroutines (Kotlin)** - Use suspend functions:
```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>
}

// Usage with coroutines
lifecycleScope.launch {
    val users = userDao.getAll()
}
```

2. **RxJava** - Return reactive types:
```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flowable<List<User>>
}
```

3. **LiveData** - For lifecycle-aware observable queries:
```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>
}
```

4. **Explicit threading** - Using `allowMainThreadQueries()` (not recommended except for tests):
```kotlin
Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
    .allowMainThreadQueries() // Not recommended for production
    .build()
```

#### 8. How do you test Room database implementations?

**Answer:** Testing Room databases typically involves:

1. **In-memory database** for tests:
```kotlin
@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        userDao = db.userDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetUser() = runBlocking {
        val user = User(1, "John", "Doe")
        userDao.insert(user)
        val users = userDao.getAll()
        assertTrue(users.contains(user))
    }
}
```

2. Key testing practices:
   * Use in-memory database (`inMemoryDatabaseBuilder`)
   * Test each DAO method independently
   * Verify database migrations
   * Use `@RunWith(AndroidJUnit4::class)` for Android testing
   * Close the database after tests

#### 9. What are indices in Room and how do you implement them?

**Answer:** Indices in Room improve query performance on specific columns but slightly decrease write performance.

```kotlin
@Entity(indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey val uid: Int,
    val email: String,
    val name: String
)
```

You can also create composite indices:
```kotlin
@Entity(indices = [
    Index(value = ["first_name", "last_name"]),
    Index(value = ["state"], unique = true)
])
data class Customer(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val state: String
)
```

#### 10. How do FTS (Full-Text Search) entities work in Room?

**Answer:** Full-Text Search allows efficient text searching in the database:

```kotlin
// Define an FTS entity
@Fts4(contentEntity = Book::class)
@Entity
data class BookFts(
    @PrimaryKey @ColumnInfo(name = "rowid") val rowId: Int,
    val title: String,
    val content: String
)

// Regular entity that will be indexed
@Entity
data class Book(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String
)

// DAO with FTS query
@Dao
interface BookDao {
    @Insert
    fun insertBook(book: Book)
    
    @Insert
    fun insertBookFts(bookFts: BookFts)
    
    @Query("SELECT * FROM Book JOIN BookFts ON Book.id = BookFts.rowid " +
           "WHERE BookFts.content MATCH :query")
    fun searchBooks(query: String): List<Book>
}
```

FTS annotations include:
* `@Fts3`: Older FTS version
* `@Fts4`: More features, better performance than FTS3
* `contentEntity`: Points to the entity containing the actual data

Common search patterns:
* Simple match: `'text'`
* Prefix search: `'text*'`
* Phrase search: `'"exact phrase"'`
* AND operator: `'text1 text2'`
* OR operator: `'text1 OR text2'`