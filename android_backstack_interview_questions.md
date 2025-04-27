# Android Backstack & Task Management Interview Questions

## Basic Concepts

### What is the Back Stack in Android?

**Answer:** The back stack in Android is a stack of activities (screens) that maintains the order in
which activities are opened. When a new activity starts, it's pushed onto the stack and becomes the
current activity. When the user presses the back button, the current activity is popped off the
stack, and the previous activity becomes visible again.

### What is a Task in Android?

**Answer:** A task in Android is a collection of activities that users interact with when performing
a certain job. It represents the complete user journey across multiple screens that belong together.
A task forms a back stack of activities arranged in the order in which each activity is opened.

### How are Tasks and the Back Stack related?

**Answer:** A task contains the back stack of activities. The back stack defines the arrangement of
activities within a task (which activity is on top and visible to the user). Multiple tasks can
exist simultaneously on an Android device, each with its own back stack.

## Launch Modes

### What are Launch Modes in Android?

**Answer:** Launch modes determine how a new activity instance should be associated with the current
task. They define the behavior when an activity is launched, particularly whether a new instance
should be created or an existing one reused, and how it should be placed in the back stack.

### Explain the "standard" launch mode.

**Answer:** Standard is the default launch mode. In this mode, a new instance of the activity is
created and pushed onto the back stack of the current task every time it's launched. Multiple
instances of the same activity can exist in the same task or across different tasks.

### What is the "singleTop" launch mode and when should it be used?

**Answer:** In singleTop mode, if an instance of the activity already exists at the top of the back
stack, that instance will be reused (with onNewIntent() called), instead of creating a new instance.
If the activity is not at the top, a new instance will be created.

It's useful when you want to avoid creating duplicate screens, such as in a notification system
where clicking multiple similar notifications shouldn't stack identical screens.

### Explain the "singleTask" launch mode.

**Answer:** With singleTask, the system creates a new task with the activity as the root if it
doesn't already exist. If an instance of the activity already exists in a separate task, the system
routes the intent to that instance through onNewIntent(), rather than creating a new one, and clears
all activities above it in the stack.

This is useful for activities that serve as entry points to applications, like a home screen or main
activity.

### When would you use the "singleInstance" launch mode?

**Answer:** SingleInstance is similar to singleTask, but the activity becomes the only activity in
its task. No other activities can be part of that task. This launch mode is very restrictive and is
typically used for activities that should never be associated with other activities, such as a
payment screen or a specialized login activity requiring isolation.

## Practical Scenarios

### How would you implement proper back navigation in a complex app?

**Answer:** For proper back navigation:

1. Follow the Android design guidelines for hierarchical navigation
2. Use appropriate launch modes for activities based on their purpose
3. Consider using the Navigation Component for managing navigation and the back stack
4. Override onBackPressed() when custom behavior is needed
5. Use parent-child relationships in the manifest with `parentActivityName`
6. For complex workflows, consider managing fragment transactions with proper back stack management

### What happens when you open another app from your app and then press back?

**Answer:** When you open another app from your app (e.g., via an intent), the system preserves your
app's task in the background. When the user presses back from the second app (assuming standard
behavior), they will return to your app, continuing from where they left off because Android
preserves the task state.

### How can you clear the back stack programmatically?

**Answer:** You can clear the back stack programmatically in several ways:

1. Using intent flags: `Intent.FLAG_ACTIVITY_CLEAR_TOP`, `Intent.FLAG_ACTIVITY_NEW_TASK`, and
   `Intent.FLAG_ACTIVITY_CLEAR_TASK`
2. Using the TaskStackBuilder API
3. Using finishAffinity() to finish all activities in the current task
4. Using the Navigation Component's popUpTo and popUpToInclusive attributes

### In which scenarios would you prefer singleTop over standard launch mode?

**Answer:** You would prefer singleTop when:

1. Handling notifications that might lead to the same screen
2. In a search interface where submitting multiple queries shouldn't stack multiple search result
   screens
3. In a bottom navigation setup where tapping the same tab repeatedly shouldn't stack instances
4. In any scenario where duplicate screens would create a poor user experience

### How does TaskAffinity affect the back stack behavior?

**Answer:** TaskAffinity defines which task an activity prefers to belong to. By default, all
activities in an app have the same affinity (the package name). When an activity with a specific
affinity is launched with `FLAG_ACTIVITY_NEW_TASK` or using launch modes like
singleTask/singleInstance, it will be placed in a task with the matching affinity or create a new
task if none exists. This can alter the expected back stack behavior as activities could be placed
in different tasks.

## Practical Examples

### Setting Launch Modes in AndroidManifest.xml

```xml
<activity
    android:name=".StandardActivity"
    android:label="Standard Activity" />

<activity
    android:name=".SingleTopActivity"
    android:launchMode="singleTop"
    android:label="SingleTop Activity" />

<activity
    android:name=".SingleTaskActivity"
    android:launchMode="singleTask"
    android:label="SingleTask Activity" />

<activity
    android:name=".SingleInstanceActivity"
    android:launchMode="singleInstance"
    android:label="SingleInstance Activity" />

<!-- Example with taskAffinity -->
<activity
    android:name=".CustomAffinityActivity"
    android:taskAffinity="com.example.customtask"
    android:launchMode="singleTask"
    android:label="Custom Affinity Activity" />
```

### Using Intent Flags for Back Stack Management

```kotlin
// Start a new activity, clearing all activities above it in the stack
fun navigateWithClearTop(context: Context) {
    val intent = Intent(context, TargetActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    context.startActivity(intent)
}

// Clear the entire back stack and start a new task
fun navigateAndClearStack(context: Context) {
    val intent = Intent(context, HomeActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
}

// Bringing an existing activity to the front
fun reopenExistingActivity(context: Context) {
    val intent = Intent(context, ExistingActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
    context.startActivity(intent)
}
```

### Handling onNewIntent for SingleTop and SingleTask Activities

```kotlin
class SearchActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        
        // Process the intent that started this activity
        handleIntent(intent)
    }
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // This will be called if an existing instance of this activity is reused
        // (when using singleTop or singleTask launch modes)
        setIntent(intent) // Update the stored intent
        handleIntent(intent)
    }
    
    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            // Perform the search operation with the new query
            performSearch(query)
        }
    }
}
```

### Programmatically Finishing Activities to Manipulate Back Stack

```kotlin
// Finish current activity
fun leaveCurrentScreen() {
    finish()
}

// Finish all activities in the task
fun closeEntireApp() {
    finishAffinity()
}

// Finish activities back to a specific point
fun finishActivitiesUntilSpecificOne() {
    // This will destroy this activity and all activities below it until it finds
    // MainActivity, then it will restore MainActivity
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
    finish()
}
```

### Real-world Example: Navigation Pattern for an E-commerce App

```kotlin
class ProductListActivity : AppCompatActivity() {
    
    override fun onProductClick(productId: String) {
        // Navigate to product details with standard launch mode
        // This allows users to view multiple products and navigate back through them
        val intent = Intent(this, ProductDetailActivity::class.java).apply {
            putExtra("PRODUCT_ID", productId)
        }
        startActivity(intent)
    }
    
    override fun onCartIconClick() {
        // Navigate to cart with singleTop to prevent multiple cart screens
        val intent = Intent(this, CartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
    }
    
    override fun onCheckoutClick() {
        // Navigate to checkout with singleTask to ensure only one checkout flow exists
        val intent = Intent(this, CheckoutActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
    
    override fun onPaymentClick() {
        // Use singleInstance for a secure payment screen
        val intent = Intent(this, PaymentActivity::class.java)
        startActivity(intent)
    }
}
```

### Navigation Component Example for Modern Apps

```kotlin
// navigation_graph.xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/homeFragment">

    <fragment
        android:id="@+id/homeFragment"
        android:name="com.example.app.HomeFragment"
        android:label="Home">
        <action
            android:id="@+id/action_home_to_detail"
            app:destination="@id/detailFragment" />
    </fragment>

    <fragment
        android:id="@+id/detailFragment"
        android:name="com.example.app.DetailFragment"
        android:label="Detail">
        <argument
            android:name="itemId"
            app:argType="string" />
        <action
            android:id="@+id/action_detail_to_checkout"
            app:destination="@id/checkoutFragment"
            app:popUpTo="@id/homeFragment" />
    </fragment>

    <fragment
        android:id="@+id/checkoutFragment"
        android:name="com.example.app.CheckoutFragment"
        android:label="Checkout">
        <action
            android:id="@+id/action_checkout_to_confirmation"
            app:destination="@id/confirmationFragment"
            app:popUpTo="@id/homeFragment"
            app:popUpToInclusive="false" />
    </fragment>

    <fragment
        android:id="@+id/confirmationFragment"
        android:name="com.example.app.ConfirmationFragment"
        android:label="Confirmation">
        <action
            android:id="@+id/action_confirmation_to_home"
            app:destination="@id/homeFragment"
            app:popUpTo="@id/homeFragment"
            app:popUpToInclusive="true" />
    </fragment>
</navigation>

// Usage in a fragment
findNavController().navigate(
    R.id.action_detail_to_checkout,
    bundleOf("orderId" to orderId)
)
```

### Testing the Back Stack Behavior

```kotlin
class BackStackTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun testBackStackNavigation() {
        // Navigate to second activity
        onView(withId(R.id.btnNavigate)).perform(click())
        
        // Verify we're on SecondActivity
        onView(withText("Second Activity")).check(matches(isDisplayed()))
        
        // Navigate to third activity
        onView(withId(R.id.btnNextScreen)).perform(click())
        
        // Verify we're on ThirdActivity
        onView(withText("Third Activity")).check(matches(isDisplayed()))
        
        // Press back and verify we return to SecondActivity
        Espresso.pressBack()
        onView(withText("Second Activity")).check(matches(isDisplayed()))
        
        // Press back again and verify we return to MainActivity
        Espresso.pressBack()
        onView(withText("Main Activity")).check(matches(isDisplayed()))
    }
}