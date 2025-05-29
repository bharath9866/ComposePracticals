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

// Constants
private const val CHANNEL_DEFAULT_ID = "default_channel"
private const val CHANNEL_HIGH_PRIORITY_ID = "high_priority_channel"
private const val CHANNEL_MEDIA_ID = "media_channel"
private const val NOTIFICATION_ID_MEDIA = 200
private const val GROUP_KEY_MESSAGES = "group_key_messages"
private val notificationIdGenerator = AtomicInteger(100)

// Action constants for PendingIntents
private const val ACTION_VIEW = NotificationReceiver.ACTION_VIEW
private const val ACTION_REPLY = NotificationReceiver.ACTION_REPLY
private const val ACTION_PREVIOUS = NotificationReceiver.ACTION_PREVIOUS
private const val ACTION_PAUSE = NotificationReceiver.ACTION_PAUSE
private const val ACTION_NEXT = NotificationReceiver.ACTION_NEXT
private const val ACTION_CUSTOM_BUTTON_1 = NotificationReceiver.ACTION_CUSTOM_BUTTON_1
private const val ACTION_CUSTOM_BUTTON_2 = NotificationReceiver.ACTION_CUSTOM_BUTTON_2
private const val KEY_TEXT_REPLY = NotificationReceiver.KEY_TEXT_REPLY

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
    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Basic Notification",
        text = "This is a simple notification from AdaptiveStreamingPlayer"
    )

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendActionNotification(context: Context, notificationManager: NotificationManager) {
    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Action Notification",
        text = "This notification contains an action button",
        smallIcon = android.R.drawable.ic_dialog_alert
    ).addAction(
        android.R.drawable.ic_media_play,
        "Open App",
        PendingIntent.getActivity(
            context, 0, Intent(context, MainActivity::class.java).apply {
                action = ACTION_VIEW
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }, PendingIntent.FLAG_IMMUTABLE
        )
    )

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendBigTextNotification(context: Context, notificationManager: NotificationManager) {
    val longText = "This is a much longer text that will be displayed when the notification is expanded. " +
            "It can contain multiple lines of text and provide more detailed information to the user. " +
            "When collapsed, only the first line will be visible."

    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Big Text Style Notification",
        text = "Expandable notification with lots of text..."
    ).setStyle(NotificationCompat.BigTextStyle()
        .bigText(longText)
        .setSummaryText("Notification summary"))

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendBigPictureNotification(context: Context, notificationManager: NotificationManager) {
    val bigPicture = BitmapFactory.decodeResource(
        context.resources,
        android.R.drawable.picture_frame
    )

    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Big Picture Style",
        text = "Expandable notification with an image",
        smallIcon = android.R.drawable.ic_menu_gallery
    )
        .setLargeIcon(bigPicture)
        .setStyle(NotificationCompat.BigPictureStyle()
            .bigPicture(bigPicture)
            .bigLargeIcon(null as Bitmap?))

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendInboxStyleNotification(context: Context, notificationManager: NotificationManager) {
    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Inbox Style",
        text = "You have 3 new messages",
        smallIcon = android.R.drawable.ic_dialog_email
    ).setStyle(NotificationCompat.InboxStyle()
        .addLine("Message 1: Hello there!")
        .addLine("Message 2: How are you doing?")
        .addLine("Message 3: Let's meet tomorrow.")
        .setBigContentTitle("3 new messages")
        .setSummaryText("user@example.com"))

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private fun sendMediaStyleNotification(context: Context, notificationManager: NotificationManager) {
    val mediaActions = createMediaActions(context)

    val builder = createBaseNotificationBuilder(
        context = context,
        channelId = CHANNEL_MEDIA_ID,
        title = "Song Title",
        text = "Artist - Album",
        smallIcon = android.R.drawable.ic_media_play,
        priority = NotificationCompat.PRIORITY_LOW,
        autoCancel = false
    )
        .setOnlyAlertOnce(true)
        .setStyle(MediaStyle().setShowActionsInCompactView(0, 1, 2))
        .addAction(mediaActions[0])
        .addAction(mediaActions[1])
        .addAction(mediaActions[2])

    notificationManager.notify(NOTIFICATION_ID_MEDIA, builder.build())
}

private fun createMediaActions(context: Context): List<NotificationCompat.Action> {
    val actions = arrayOf(
        Triple(
            ACTION_PREVIOUS,
            "Previous",
            android.R.drawable.ic_media_previous
        ),
        Triple(
            ACTION_PAUSE,
            "Pause",
            android.R.drawable.ic_media_pause
        ),
        Triple(
            ACTION_NEXT,
            "Next",
            android.R.drawable.ic_media_next
        )
    )

    return actions.mapIndexed { index, (action, title, icon) ->
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            this.action = action
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, index + 1, intent, PendingIntent.FLAG_IMMUTABLE
        )
        NotificationCompat.Action(icon, title, pendingIntent)
    }
}

private fun sendMessagingStyleNotification(context: Context, notificationManager: NotificationManager) {
    val currentTime = System.currentTimeMillis()

    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Chat Messages",
        text = "New messages in your chat",
        smallIcon = android.R.drawable.ic_dialog_email
    ).setStyle(NotificationCompat.MessagingStyle("Me")
        .setConversationTitle("Group Chat")
        .addMessage("Hi, how are you?", currentTime - 3000, "John")
        .addMessage("I'm fine, thanks!", currentTime - 2000, "Me")
        .addMessage("What about the meeting?", currentTime - 1000, "John"))

    notificationManager.notify(getNextNotificationId(), builder.build())
}

private suspend fun sendProgressNotification(context: Context, notificationManager: NotificationManager) {
    val notificationId = getNextNotificationId()

    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Download in Progress",
        text = "Download starting...",
        smallIcon = android.R.drawable.stat_sys_download,
        priority = NotificationCompat.PRIORITY_LOW,
        autoCancel = false
    ).setOngoing(true)

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
    val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
        .setLabel(replyLabel)
        .build()

    // PendingIntent for the reply action using BroadcastReceiver
    val replyIntent = Intent(context, NotificationReceiver::class.java).apply {
        action = ACTION_REPLY
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

    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Direct Reply Notification",
        text = "Reply to this message directly from the notification",
        smallIcon = android.R.drawable.ic_dialog_email
    ).addAction(action)

    notificationManager.notify(notificationId, builder.build())
}

private fun sendCustomLayoutNotification(context: Context, notificationManager: NotificationManager) {
    val notificationId = getNextNotificationId()
    
    // Create custom views for collapsed and expanded states
    val notificationLayout = createSmallCustomLayout(context, notificationId)
    val notificationLayoutExpanded = createLargeCustomLayout(context, notificationId)

    val builder = createBaseNotificationBuilder(
        context = context,
        title = "Custom Notification",
        text = "This is a custom notification",
        smallIcon = R.drawable.ic_launcher_foreground
    )
        .setCustomContentView(notificationLayout)
        .setCustomBigContentView(notificationLayoutExpanded)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())

    notificationManager.notify(notificationId, builder.build())
}

private fun createSmallCustomLayout(context: Context, notificationId: Int): RemoteViews {
    return RemoteViews(context.packageName, R.layout.notification_small).apply {
        setTextViewText(R.id.notification_title, "Custom Notification")
        setTextViewText(R.id.notification_text, "This is a custom notification layout")
        setImageViewResource(R.id.notification_icon, R.drawable.ic_launcher_foreground)
    }
}

private fun createLargeCustomLayout(context: Context, notificationId: Int): RemoteViews {
    val layoutExpanded = RemoteViews(context.packageName, R.layout.notification_large).apply {
        setTextViewText(R.id.notification_large_title, "Expanded Custom Notification")
        setTextViewText(
            R.id.notification_large_text, 
            "This is an expanded custom layout with more details. You can see much more information here than in the collapsed view."
        )
        setImageViewResource(R.id.notification_large_icon, R.drawable.ic_launcher_foreground)
    }
    
    // Set up button click actions
    val buttonActions = arrayOf(
        Pair(ACTION_CUSTOM_BUTTON_1, R.id.notification_button1),
        Pair(ACTION_CUSTOM_BUTTON_2, R.id.notification_button2)
    )
    
    buttonActions.forEachIndexed { index, (action, buttonId) ->
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            this.action = action
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, 10 + index, intent, PendingIntent.FLAG_IMMUTABLE
        )
        layoutExpanded.setOnClickPendingIntent(buttonId, pendingIntent)
    }
    
    return layoutExpanded
}

private fun sendGroupedNotifications(context: Context, notificationManager: NotificationManager) {
    // Create individual message notifications
    val messageData = listOf(
        Triple("Message from John", "Hey, how are you?", getNextNotificationId()),
        Triple("Message from Sarah", "Are we still meeting tomorrow?", getNextNotificationId()),
        Triple("Message from Mike", "I've sent you the files you requested", getNextNotificationId())
    )
    
    messageData.forEach { (title, text, id) ->
        val builder = createBaseNotificationBuilder(
            context = context,
            title = title,
            text = text,
            smallIcon = android.R.drawable.ic_dialog_email
        ).setGroup(GROUP_KEY_MESSAGES)
        
        notificationManager.notify(id, builder.build())
    }

    // Create summary notification
    val summaryBuilder = createBaseNotificationBuilder(
        context = context,
        title = "3 new messages",
        text = "John, Sarah, and Mike",
        smallIcon = android.R.drawable.ic_dialog_email
    )
        .setGroup(GROUP_KEY_MESSAGES)
        .setGroupSummary(true)

    notificationManager.notify(getNextNotificationId(), summaryBuilder.build())
}

private fun sendHeadsUpNotification(context: Context, notificationManager: NotificationManager) {
    val builder = createBaseNotificationBuilder(
        context = context,
        channelId = CHANNEL_HIGH_PRIORITY_ID,
        title = "Urgent Notification",
        text = "This is a high-priority heads-up notification!",
        priority = NotificationCompat.PRIORITY_HIGH,
        smallIcon = android.R.drawable.ic_dialog_alert
    )
        .setVibrate(longArrayOf(0, 500, 250, 500))
        .setLights(Color.RED, 3000, 3000)

    notificationManager.notify(getNextNotificationId(), builder.build())
}

// Helper function to get unique notification IDs
private fun getNextNotificationId(): Int = notificationIdGenerator.incrementAndGet()

// Helper function to create basic notification builder
private fun createBaseNotificationBuilder(
    context: Context, 
    channelId: String = CHANNEL_DEFAULT_ID,
    title: String,
    text: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT,
    smallIcon: Int = android.R.drawable.ic_dialog_info,
    autoCancel: Boolean = true
): NotificationCompat.Builder {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    return NotificationCompat.Builder(context, channelId)
        .setSmallIcon(smallIcon)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(priority)
        .setContentIntent(pendingIntent)
        .setAutoCancel(autoCancel)
}