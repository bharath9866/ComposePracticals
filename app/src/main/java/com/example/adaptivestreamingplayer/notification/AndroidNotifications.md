# Android Notifications Guide

## Table of Contents

- [Introduction to Notifications](#introduction-to-notifications)
- [Notification Components](#notification-components)
- [Creating Basic Notifications](#creating-basic-notifications)
- [Notification Channels](#notification-channels)
- [Notification Permissions](#notification-permissions)
- [Types of Notifications](#types-of-notifications)
- [Notification Actions](#notification-actions)
- [Notification Styles](#notification-styles)
- [Expandable Notifications](#expandable-notifications)
- [Heads-Up Notifications](#heads-up-notifications)
- [Progress Notifications](#progress-notifications)
- [Direct Reply](#direct-reply)
- [Custom Notification Layouts](#custom-notification-layouts)
- [Notification Groups](#notification-groups)
- [Notification Importance and Priority](#notification-importance-and-priority)
- [Foreground Service Notifications](#foreground-service-notifications)
- [DND Mode and Notifications](#dnd-mode-and-notifications)
- [Best Practices](#best-practices)
- [Interview Questions](#interview-questions)

## Introduction to Notifications

Notifications in Android provide a way to alert users about events in your app when it's not in use.
They appear in the system's notification area and can be expanded to show more details.

Notifications are an essential part of Android's user experience, allowing apps to remain engaged
with users even when they are not actively using the application.

## Notification Components

A basic notification consists of these components:

- **Small icon**: Required. Set with `setSmallIcon()`
- **Title**: Required. Set with `setContentTitle()`
- **Body text**: Required. Set with `setContentText()`
- **Notification priority/importance**: Controls notification's intrusiveness, set with
  `setPriority()` (pre-O) or by channel importance (O+)
- **Intent to handle clicks**: Optional. Set with `setContentIntent()`
- **Actions**: Optional. Set with `addAction()`

## Creating Basic Notifications

### Basic Notification Implementation

```kotlin
private fun sendBasicNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )
    
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Basic Notification")
        .setContentText("This is a simple notification from the app")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
    
    notificationManager.notify(NOTIFICATION_ID, builder.build())
}
```

## Notification Channels

Starting from Android 8.0 (API level 26), all notifications must be assigned to a channel. Users can
control the importance and visibility settings for each notification channel.

### Creating a Notification Channel

```kotlin
private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "General Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "General notifications from the app"
        }
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
```

## Notification Permissions

Starting from Android 13 (API level 33), apps must request the POST_NOTIFICATIONS permission to
display notifications.

### Manifest Declaration

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

### Requesting Notification Permission

```kotlin
val requestPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { isGranted ->
        if (isGranted) {
            // Permission granted, can show notifications
        } else {
            // Explain why notifications are needed
        }
    }
)

// Check if permission is already granted
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    if (ContextCompat.checkSelfPermission(
        context, Manifest.permission.POST_NOTIFICATIONS) != 
        PackageManager.PERMISSION_GRANTED) {
        // Request permission
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}
```

## Types of Notifications

### Standard Notification

Basic notification with title, text and icon.

### Expandable Notification

Notification that can be expanded to show more content.

### Media Control Notification

Specialized notification for media playback with controls.

### Messaging Notification

Notification optimized for chat messages.

## Notification Actions

You can add action buttons to notifications to provide quick ways for users to respond.

```kotlin
val snoozeIntent = Intent(context, NotificationReceiver::class.java).apply {
    action = "SNOOZE_ACTION"
}
val snoozePendingIntent = PendingIntent.getBroadcast(
    context, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE
)

val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .addAction(
        R.drawable.ic_snooze, 
        "Snooze", 
        snoozePendingIntent
    )
```

## Notification Styles

Android provides different styles for notifications to enhance their appearance:

### Big Text Style

```kotlin
val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setStyle(NotificationCompat.BigTextStyle()
        .bigText("This is a much longer text that will be displayed when the notification is expanded. It can contain multiple lines of text and provide more detailed information to the user."))
```

### Big Picture Style

```kotlin
val bigPicture = BitmapFactory.decodeResource(context.resources, R.drawable.large_image)

val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setStyle(NotificationCompat.BigPictureStyle()
        .bigPicture(bigPicture)
        .bigLargeIcon(null)) // Hide the large icon when expanded
```

### Inbox Style

```kotlin
val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setStyle(NotificationCompat.InboxStyle()
        .addLine("First message")
        .addLine("Second message")
        .addLine("Third message")
        .setBigContentTitle("3 new messages")
        .setSummaryText("From John Smith"))
```

### Media Style

```kotlin
val mediaSession = MediaSessionCompat(context, "MediaSessionTag")

val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
        .setMediaSession(mediaSession.sessionToken)
        .setShowActionsInCompactView(0, 1, 2))
    .addAction(R.drawable.ic_prev, "Previous", prevPendingIntent)
    .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)
    .addAction(R.drawable.ic_next, "Next", nextPendingIntent)
```

### Messaging Style

```kotlin
val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setStyle(NotificationCompat.MessagingStyle("Me")
        .setConversationTitle("Group Chat")
        .addMessage("Hi, how are you?", System.currentTimeMillis(), "John")
        .addMessage("I'm fine, thanks!", System.currentTimeMillis(), "Me")
        .addMessage("What about the meeting?", System.currentTimeMillis(), "John"))
```

## Expandable Notifications

Expandable notifications allow users to see more content when they expand the notification:

```kotlin
val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setContentText("Short message")
    .setStyle(NotificationCompat.BigTextStyle()
        .bigText("This is the expanded text that will be shown when the user expands the notification. It can contain much more information than would fit in the collapsed view."))
```

## Heads-Up Notifications

High-priority notifications can appear as floating alerts (heads-up notifications):

```kotlin
val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setPriority(NotificationCompat.PRIORITY_HIGH)
    .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
    .setLights(Color.RED, 3000, 3000)
```

For Android 8.0+, you need to set the channel importance to `IMPORTANCE_HIGH`.

## Progress Notifications

You can display a progress indicator in a notification:

```kotlin
val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setProgress(100, 50, false) // max value, current progress, indeterminate

// Update progress
for (progress in 0..100 step 10) {
    builder.setProgress(100, progress, false)
    notificationManager.notify(NOTIFICATION_ID, builder.build())
    Thread.sleep(1000) // Just for demonstration
}

// When done, update the notification one more time
builder.setContentText("Download complete")
    .setProgress(0, 0, false)
notificationManager.notify(NOTIFICATION_ID, builder.build())
```

## Direct Reply

Direct reply allows users to respond to a notification without opening the app:

```kotlin
val replyLabel = "Reply"
val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
    .setLabel(replyLabel)
    .build()

val replyIntent = Intent(context, NotificationReceiver::class.java)
val replyPendingIntent = PendingIntent.getBroadcast(
    context, 0, replyIntent, PendingIntent.FLAG_MUTABLE
)

val action = NotificationCompat.Action.Builder(
    R.drawable.ic_reply,
    "Reply",
    replyPendingIntent
)
    .addRemoteInput(remoteInput)
    .build()

val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .addAction(action)
```

## Custom Notification Layouts

For complex notification designs, you can use custom layouts:

```kotlin
val notificationLayout = RemoteViews(context.packageName, R.layout.notification_small)
val notificationLayoutExpanded = RemoteViews(context.packageName, R.layout.notification_large)

val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
    .setCustomContentView(notificationLayout)
    .setCustomBigContentView(notificationLayoutExpanded)
```

## Notification Groups

Group multiple notifications together:

```kotlin
// For each notification in the group
val builder1 = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setGroup(GROUP_KEY)

val builder2 = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setGroup(GROUP_KEY)

// Summary notification
val summaryBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
    // ... Basic notification settings ...
    .setGroup(GROUP_KEY)
    .setGroupSummary(true)
    .setContentTitle("2 new messages")

// Notify
notificationManager.notify(1, builder1.build())
notificationManager.notify(2, builder2.build())
notificationManager.notify(SUMMARY_ID, summaryBuilder.build())
```

## Notification Importance and Priority

For Android 8.0+ (API level 26+), importance is determined by the notification channel. For earlier
versions, you use the `setPriority()` method.

Importance levels:

- `IMPORTANCE_HIGH`: Makes a sound and appears as a heads-up notification
- `IMPORTANCE_DEFAULT`: Makes a sound
- `IMPORTANCE_LOW`: No sound
- `IMPORTANCE_MIN`: No sound and doesn't appear in the status bar

Priority levels (before Android 8.0):

- `PRIORITY_MAX`: For critical and urgent notifications
- `PRIORITY_HIGH`: For important notifications
- `PRIORITY_DEFAULT`: For regular notifications
- `PRIORITY_LOW`: For non-urgent notifications
- `PRIORITY_MIN`: For background notifications

## Foreground Service Notifications

Foreground services must display a notification:

```kotlin
val notification = NotificationCompat.Builder(context, CHANNEL_ID)
    .setContentTitle("App is running")
    .setContentText("Doing background work")
    .setSmallIcon(R.drawable.ic_notification)
    .build()

startForeground(NOTIFICATION_ID, notification)
```

## DND Mode and Notifications

Some notifications can bypass DND (Do Not Disturb) mode:

```kotlin
// For Android M and above
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    
    // Check if the app can bypass DND mode
    if (!notificationManager.isNotificationPolicyAccessGranted) {
        // Request permission
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        context.startActivity(intent)
    }
}
```

## Best Practices

1. **Use notification channels effectively**: Create distinct channels for different types of
   notifications.
2. **Respect user's settings**: Don't circumvent or ignore a user's notification preferences.
3. **Provide useful actions**: Add relevant actions that help users respond without opening the app.
4. **Update notifications** instead of creating new ones for the same event.
5. **Use proper importance levels**: Reserve high-priority notifications for time-sensitive or
   critical information.
6. **Group related notifications**: Use notification groups to avoid overwhelming the user.
7. **Clear notifications**: Remove notifications when they're no longer relevant.
8. **Test on multiple devices**: Ensure consistent behavior across different Android versions.
9. **Make notifications accessible**: Use clear icons, contrasting colors, and descriptive text.
10. **Respect battery life**: Avoid excessive notifications that might drain the battery.

## Interview Questions

### Basic Questions

1. **What is a notification in Android?**
    - A notification is a message that Android displays outside your app's UI to provide users with
      reminders, communication from other people, or other timely information from your app.

2. **What is the difference between notifications in Android pre-Oreo and post-Oreo?**
    - Before Android 8.0 (Oreo), notifications were configured individually. After Oreo,
      notifications must be assigned to notification channels, which allow users to control
      notification importance and behavior at a more granular level.

3. **What is a NotificationChannel and why is it important?**
    - A NotificationChannel represents a type of notification that your app sends. It's important
      because it gives users control over which types of notifications they receive from your app
      and how intrusive they are.

4. **How do you create a basic notification in Android?**
    - You create a notification using the NotificationCompat.Builder class, set its properties (
      icon, title, text), and then use NotificationManager.notify() to show it.

### Intermediate Questions

5. **How do you handle notification permission in Android 13 and above?**
    - Starting from Android 13, you need to request the POST_NOTIFICATIONS permission at runtime
      using the permission request system, in addition to declaring it in the manifest.

6. **What is a PendingIntent and why is it used with notifications?**
    - A PendingIntent is a token that you give to another application which allows the other
      application to use your application's permissions to execute a predefined piece of code. In
      notifications, it's used to define what happens when a user interacts with the notification.

7. **What are notification styles and what types are available?**
    - Notification styles define the expanded layout of a notification. Types include BigTextStyle,
      BigPictureStyle, InboxStyle, MediaStyle, and MessagingStyle.

8. **What is the purpose of setAutoCancel() in notifications?**
    - setAutoCancel(true) automatically removes the notification from the notification drawer when
      the user taps it.

### Advanced Questions

9. **How do you create a notification with direct reply action?**
    - Direct reply uses RemoteInput.Builder to create an input field, then adds it to a
      NotificationCompat.Action that is added to the notification. The reply is processed through a
      BroadcastReceiver.

10. **How do you update an existing notification instead of creating a new one?**
    - You use the same notification ID when calling NotificationManager.notify() to update an
      existing notification rather than creating a new one.

11. **What is the purpose of FLAG_IMMUTABLE and FLAG_MUTABLE in PendingIntent?**
    - FLAG_IMMUTABLE creates a PendingIntent that cannot be modified, while FLAG_MUTABLE allows its
      modification. Using FLAG_IMMUTABLE is preferred for security reasons unless you need to fill
      in unpredictable values (like for RemoteInput).

12. **How do you implement custom notification layouts?**
    - You create custom layouts using RemoteViews, then apply them to notifications using
      setCustomContentView() and setCustomBigContentView().

13. **How do you handle notification interaction when your app has multiple entries in the back
    stack?**
    - You can use TaskStackBuilder to create a back stack that allows users to navigate up from the
      notification's target activity to your app's parent activities.

14. **How can you make your notification appear as a heads-up notification?**
    - For pre-Oreo, set the notification priority to HIGH or MAX and include sound or vibration. For
      Oreo and above, create a notification channel with IMPORTANCE_HIGH.

15. **How do you implement a progress notification?**
    - Use the setProgress() method on the NotificationCompat.Builder to display a progress bar, then
      update it periodically by reissuing the notification with the same ID.

16. **What considerations should be made when implementing notifications for different Android
    versions?**
    - Account for notification channels in Android 8.0+, runtime permissions in Android 13+, and
      different visual appearances across versions. Use NotificationCompat from AndroidX for
      backward compatibility.

17. **How would you debug issues with notifications not appearing?**
    - Check if the notification channel is created properly, if the user has disabled notifications
      for your app or channel, if the notification importance is set correctly, and if all required
      permissions are granted.

18. **What is the difference between a service notification and a regular notification?**
    - A service notification is associated with a foreground service and cannot be dismissed by the
      user as long as the service is running. Regular notifications can be dismissed.

19. **What are notification categories and how are they used?**
    - Notification categories (setCategory()) are used to help the system properly handle
      notifications, particularly for Do Not Disturb mode. Categories include CATEGORY_CALL,
      CATEGORY_MESSAGE, CATEGORY_ALARM, etc.

20. **How do you handle notification groups and what is their purpose?**
    - Notification groups are created using setGroup() and setGroupSummary() to organize multiple
      related notifications under a single parent notification, reducing clutter in the notification
      drawer.
