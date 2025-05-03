package com.example.adaptivestreamingplayer.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.navigation.NavController
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.core.MainActivity
import com.example.adaptivestreamingplayer.notification.NotificationReceiver
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

@Composable
fun NotificationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val notificationManager = remember {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    val scope = rememberCoroutineScope()

    // Create notification channels for Android O and above
    LaunchedEffect(Unit) {
        createNotificationChannels(context)
    }

    // Check notification permission
    val hasNotificationPermission = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    var permissionRequested by remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionRequested = true
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Notification Examples",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission && !permissionRequested) {
            Button(onClick = {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }) {
                Text("Request Notification Permission")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Basic Notifications Section
        SectionHeader("Basic Notifications")

        // Basic Notification
        NotificationButton(text = "Basic Notification") {
            sendBasicNotification(context, notificationManager)
        }

        // Notification with Action
        NotificationButton(text = "Notification with Action") {
            sendActionNotification(context, notificationManager)
        }

        Divider()

        // Notification Styles Section
        SectionHeader("Notification Styles")

        // Big Text Style
        NotificationButton(text = "Big Text Style") {
            sendBigTextNotification(context, notificationManager)
        }

        // Big Picture Style
        NotificationButton(text = "Big Picture Style") {
            sendBigPictureNotification(context, notificationManager)
        }

        // Inbox Style
        NotificationButton(text = "Inbox Style") {
            sendInboxStyleNotification(context, notificationManager)
        }

        // Media Style
        NotificationButton(text = "Media Style") {
            sendMediaStyleNotification(context, notificationManager)
        }

        // Messaging Style
        NotificationButton(text = "Messaging Style") {
            sendMessagingStyleNotification(context, notificationManager)
        }

        Divider()

        // Advanced Notifications Section
        SectionHeader("Advanced Notifications")

        // Progress Notification
        NotificationButton(text = "Progress Notification") {
            scope.launch {
                sendProgressNotification(context, notificationManager)
            }
        }

        // Direct Reply Notification
        NotificationButton(text = "Direct Reply") {
            sendDirectReplyNotification(context, notificationManager)
        }

        // Custom Layout Notification
        NotificationButton(text = "Custom Layout") {
            sendCustomLayoutNotification(context, notificationManager)
        }

        // Grouped Notifications
        NotificationButton(text = "Grouped Notifications") {
            sendGroupedNotifications(context, notificationManager)
        }

        // Heads-Up Notification
        NotificationButton(text = "Heads-Up Notification") {
            sendHeadsUpNotification(context, notificationManager)
        }

        Divider()

        // Navigation
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun NotificationButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}

private fun createNotificationChannels(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Default Channel
        val defaultChannel = NotificationChannel(
            CHANNEL_DEFAULT_ID,
            "Default Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Default notifications from the app"
        }

        // High Priority Channel
        val highPriorityChannel = NotificationChannel(
            CHANNEL_HIGH_PRIORITY_ID,
            "Important Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Important notifications that require immediate attention"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
        }

        // Media Channel
        val mediaChannel = NotificationChannel(
            CHANNEL_MEDIA_ID,
            "Media Notifications",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Media playback notifications"
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannels(
            listOf(defaultChannel, highPriorityChannel, mediaChannel)
        )
    }
}

private fun sendBasicNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Basic Notification")
        .setContentText("This is a simple notification from AdaptiveStreamingPlayer")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendActionNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    // Action Intent
    val actionIntent = Intent(context, MainActivity::class.java).apply {
        action = NotificationReceiver.ACTION_VIEW
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val actionPendingIntent = PendingIntent.getActivity(
        context, 0, actionIntent, PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_alert)
        .setContentTitle("Action Notification")
        .setContentText("This notification contains an action button")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .addAction(
            android.R.drawable.ic_media_play,
            "Open App",
            actionPendingIntent
        )

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendBigTextNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    val longText = "This is a much longer text that will be displayed when the notification is expanded. " +
            "It can contain multiple lines of text and provide more detailed information to the user. " +
            "When collapsed, only the first line will be visible."

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Big Text Style Notification")
        .setContentText("Expandable notification with lots of text...")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setStyle(NotificationCompat.BigTextStyle()
            .bigText(longText)
            .setSummaryText("Notification summary"))

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendBigPictureNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    // Using a default Android drawable for the example
    val bigPicture = BitmapFactory.decodeResource(
        context.resources,
        android.R.drawable.picture_frame
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_menu_gallery)
        .setContentTitle("Big Picture Style")
        .setContentText("Expandable notification with an image")
        .setLargeIcon(bigPicture)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setStyle(NotificationCompat.BigPictureStyle()
            .bigPicture(bigPicture)
            .bigLargeIcon(null as Bitmap?))

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendInboxStyleNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Inbox Style")
        .setContentText("You have 3 new messages")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setStyle(NotificationCompat.InboxStyle()
            .addLine("Message 1: Hello there!")
            .addLine("Message 2: How are you doing?")
            .addLine("Message 3: Let's meet tomorrow.")
            .setBigContentTitle("3 new messages")
            .setSummaryText("user@example.com"))

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendMediaStyleNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    // Media control actions
    val prevIntent = Intent(context, NotificationReceiver::class.java).apply {
        action = NotificationReceiver.ACTION_PREVIOUS
    }
    val prevPendingIntent = PendingIntent.getBroadcast(
        context, 1, prevIntent, PendingIntent.FLAG_IMMUTABLE
    )

    val pauseIntent = Intent(context, NotificationReceiver::class.java).apply {
        action = NotificationReceiver.ACTION_PAUSE
    }
    val pausePendingIntent = PendingIntent.getBroadcast(
        context, 2, pauseIntent, PendingIntent.FLAG_IMMUTABLE
    )

    val nextIntent = Intent(context, NotificationReceiver::class.java).apply {
        action = NotificationReceiver.ACTION_NEXT
    }
    val nextPendingIntent = PendingIntent.getBroadcast(
        context, 3, nextIntent, PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_MEDIA_ID)
        .setSmallIcon(android.R.drawable.ic_media_play)
        .setContentTitle("Song Title")
        .setContentText("Artist - Album")
        .setContentIntent(pendingIntent)
        .setOnlyAlertOnce(true)
        .setAutoCancel(false)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setStyle(MediaStyle()
            .setShowActionsInCompactView(0, 1, 2))
        .addAction(android.R.drawable.ic_media_previous, "Previous", prevPendingIntent)
        .addAction(android.R.drawable.ic_media_pause, "Pause", pausePendingIntent)
        .addAction(android.R.drawable.ic_media_next, "Next", nextPendingIntent)

    notificationManager.notify(NOTIFICATION_ID_MEDIA, builder.build())
}

private fun sendMessagingStyleNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    val currentTime = System.currentTimeMillis()

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Chat Messages")
        .setContentText("New messages in your chat")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setStyle(NotificationCompat.MessagingStyle("Me")
            .setConversationTitle("Group Chat")
            .addMessage("Hi, how are you?", currentTime - 3000, "John")
            .addMessage("I'm fine, thanks!", currentTime - 2000, "Me")
            .addMessage("What about the meeting?", currentTime - 1000, "John"))

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private suspend fun sendProgressNotification(context: Context, notificationManager: NotificationManager) {
    val notificationId = getNextNotificationId()

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.stat_sys_download)
        .setContentTitle("Download in Progress")
        .setContentText("Download starting...")
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setOngoing(true)

    // Simulating a download progress
    for (progress in 0..100 step 10) {
        builder.setProgress(100, progress, false)
        builder.setContentText("Downloading: $progress%")
        notificationManager.notify(notificationId, builder.build())

        // Simulate delay
        kotlinx.coroutines.delay(500)
    }

    // Download complete
    builder.setContentText("Download complete")
        .setProgress(0, 0, false)
        .setOngoing(false)
        .setAutoCancel(true)
    notificationManager.notify(notificationId, builder.build())
}

private fun sendDirectReplyNotification(context: Context, notificationManager: NotificationManager) {
    val notificationId = getNextNotificationId()
    
    val replyLabel = "Reply"
    val remoteInput = RemoteInput.Builder(NotificationReceiver.KEY_TEXT_REPLY)
        .setLabel(replyLabel)
        .build()

    // PendingIntent for the reply action using BroadcastReceiver
    val replyIntent = Intent(context, NotificationReceiver::class.java).apply {
        action = NotificationReceiver.ACTION_REPLY
        putExtra("notification_id", notificationId)
    }
    val replyPendingIntent = PendingIntent.getBroadcast(
        context, 0, replyIntent, PendingIntent.FLAG_MUTABLE
    )

    val action = NotificationCompat.Action.Builder(
        android.R.drawable.ic_menu_send,
        "Reply",
        replyPendingIntent
    )
        .addRemoteInput(remoteInput)
        .build()

    // Main content intent
    val contentIntent = Intent(context, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Direct Reply Notification")
        .setContentText("Reply to this message directly from the notification")
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .addAction(action)

    notificationManager.notify(notificationId, builder.build())
}

private fun sendCustomLayoutNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    // Create custom views for collapsed and expanded states
    val notificationLayout = RemoteViews(context.packageName, R.layout.notification_small)
    notificationLayout.setTextViewText(R.id.notification_title, "Custom Notification")
    notificationLayout.setTextViewText(R.id.notification_text, "This is a custom notification layout")
    notificationLayout.setImageViewResource(R.id.notification_icon, R.drawable.ic_launcher_foreground)

    val notificationLayoutExpanded = RemoteViews(context.packageName, R.layout.notification_large)
    notificationLayoutExpanded.setTextViewText(R.id.notification_large_title, "Expanded Custom Notification")
    notificationLayoutExpanded.setTextViewText(
        R.id.notification_large_text, 
        "This is an expanded custom layout with more details. You can see much more information here than in the collapsed view."
    )
    notificationLayoutExpanded.setImageViewResource(R.id.notification_large_icon, R.drawable.ic_launcher_foreground)
    
    // Set up button click actions
    val action1Intent = Intent(context, NotificationReceiver::class.java).apply {
        action = NotificationReceiver.ACTION_CUSTOM_BUTTON_1
    }
    val action1PendingIntent = PendingIntent.getBroadcast(
        context, 10, action1Intent, PendingIntent.FLAG_IMMUTABLE
    )
    notificationLayoutExpanded.setOnClickPendingIntent(R.id.notification_button1, action1PendingIntent)
    
    val action2Intent = Intent(context, NotificationReceiver::class.java).apply {
        action = NotificationReceiver.ACTION_CUSTOM_BUTTON_2
    }
    val action2PendingIntent = PendingIntent.getBroadcast(
        context, 11, action2Intent, PendingIntent.FLAG_IMMUTABLE
    )
    notificationLayoutExpanded.setOnClickPendingIntent(R.id.notification_button2, action2PendingIntent)

    val builder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        // Set fallback content for systems that don't display custom views
        .setContentTitle("Custom Notification")
        .setContentText("This is a custom notification")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setCustomContentView(notificationLayout)
        .setCustomBigContentView(notificationLayoutExpanded)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendGroupedNotifications(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    // First notification in group
    val builder1 = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Message from John")
        .setContentText("Hey, how are you?")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setGroup(GROUP_KEY_MESSAGES)

    // Second notification in group
    val builder2 = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Message from Sarah")
        .setContentText("Are we still meeting tomorrow?")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setGroup(GROUP_KEY_MESSAGES)

    // Third notification in group
    val builder3 = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Message from Mike")
        .setContentText("I've sent you the files you requested")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setGroup(GROUP_KEY_MESSAGES)

    // Summary notification for the group
    val summaryBuilder = NotificationCompat.Builder(context, CHANNEL_DEFAULT_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("3 new messages")
        .setContentText("John, Sarah, and Mike")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setGroup(GROUP_KEY_MESSAGES)
        .setGroupSummary(true)

    // Create notification IDs
    val id1 = getNextNotificationId()
    val id2 = getNextNotificationId()
    val id3 = getNextNotificationId()
    val summaryId = getNextNotificationId()

    // Post all notifications
    notificationManager.notify(id1, builder1.build())
    notificationManager.notify(id2, builder2.build())
    notificationManager.notify(id3, builder3.build())
    notificationManager.notify(summaryId, summaryBuilder.build())
}

private fun sendHeadsUpNotification(context: Context, notificationManager: NotificationManager) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_HIGH_PRIORITY_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_alert)
        .setContentTitle("Urgent Notification")
        .setContentText("This is a high-priority heads-up notification!")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(longArrayOf(0, 500, 250, 500))
        .setLights(Color.RED, 3000, 3000)

    notificationManager.notify(getNextNotificationId(), builder.build())
}

// Helper function to get unique notification IDs
private fun getNextNotificationId(): Int = notificationIdGenerator.incrementAndGet()

// Constants
private val notificationIdGenerator = AtomicInteger(100)
private const val CHANNEL_DEFAULT_ID = "default_channel"
private const val CHANNEL_HIGH_PRIORITY_ID = "high_priority_channel"
private const val CHANNEL_MEDIA_ID = "media_channel"
private const val NOTIFICATION_ID_MEDIA = 200
private const val GROUP_KEY_MESSAGES = "group_key_messages"

// Action constants for PendingIntents
private const val ACTION_VIEW = NotificationReceiver.ACTION_VIEW
private const val ACTION_REPLY = NotificationReceiver.ACTION_REPLY
private const val ACTION_PREVIOUS = NotificationReceiver.ACTION_PREVIOUS
private const val ACTION_PAUSE = NotificationReceiver.ACTION_PAUSE
private const val ACTION_NEXT = NotificationReceiver.ACTION_NEXT
private const val ACTION_CUSTOM_BUTTON_1 = NotificationReceiver.ACTION_CUSTOM_BUTTON_1
private const val ACTION_CUSTOM_BUTTON_2 = NotificationReceiver.ACTION_CUSTOM_BUTTON_2
private const val KEY_TEXT_REPLY = NotificationReceiver.KEY_TEXT_REPLY