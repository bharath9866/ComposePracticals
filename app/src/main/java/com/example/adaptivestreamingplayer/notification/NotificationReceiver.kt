package com.example.adaptivestreamingplayer.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

/**
 * BroadcastReceiver to handle notification actions
 */
class NotificationReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "NotificationReceiver"
        
        // Action constants (must match those in NotificationScreen)
        const val ACTION_VIEW = "com.example.action.VIEW"
        const val ACTION_REPLY = "com.example.action.REPLY"
        const val ACTION_DISMISS = "com.example.action.DISMISS"
        const val ACTION_SNOOZE = "com.example.action.SNOOZE"
        const val ACTION_PREVIOUS = "com.example.action.PREVIOUS"
        const val ACTION_PAUSE = "com.example.action.PAUSE"
        const val ACTION_NEXT = "com.example.action.NEXT"
        const val ACTION_CUSTOM_BUTTON_1 = "com.example.action.CUSTOM_BUTTON_1"
        const val ACTION_CUSTOM_BUTTON_2 = "com.example.action.CUSTOM_BUTTON_2"
        
        // Key for reply text
        const val KEY_TEXT_REPLY = "key_text_reply"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_REPLY -> handleReply(context, intent)
            ACTION_DISMISS -> handleDismiss(context, intent)
            ACTION_SNOOZE -> handleSnooze(context, intent)
            ACTION_CUSTOM_BUTTON_1 -> handleCustomButton1(context)
            ACTION_CUSTOM_BUTTON_2 -> handleCustomButton2(context)
            ACTION_VIEW -> handleView(context, intent)
            ACTION_PREVIOUS -> handlePrevious(context, intent)
            ACTION_PAUSE -> handlePause(context, intent)
            ACTION_NEXT -> handleNext(context, intent)
        }
    }

    private fun handleReply(context: Context, intent: Intent) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val replyText = remoteInput.getCharSequence(KEY_TEXT_REPLY)
            if (replyText != null) {
                Log.d(TAG, "Reply received: $replyText")
                Toast.makeText(context, "Reply received: $replyText", Toast.LENGTH_SHORT).show()
                
                // In a real app, you would process the reply here
                
                // Cancel the notification
                val notificationId = intent.getIntExtra("notification_id", 0)
                if (notificationId != 0) {
                    val notificationManager = NotificationManagerCompat.from(context)
                    notificationManager.cancel(notificationId)
                }
            }
        }
    }

    private fun handleDismiss(context: Context, intent: Intent) {
        Log.d(TAG, "Notification dismissed")
        Toast.makeText(context, "Notification dismissed", Toast.LENGTH_SHORT).show()
        
        // Cancel the notification
        val notificationId = intent.getIntExtra("notification_id", 0)
        if (notificationId != 0) {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.cancel(notificationId)
        }
    }

    private fun handleSnooze(context: Context, intent: Intent) {
        Log.d(TAG, "Notification snoozed")
        Toast.makeText(context, "Notification snoozed", Toast.LENGTH_SHORT).show()
        
        // In a real app, you would reschedule the notification
    }

    private fun handleCustomButton1(context: Context) {
        Log.d(TAG, "Custom button 1 clicked")
        Toast.makeText(context, "Custom Button 1 clicked", Toast.LENGTH_SHORT).show()
    }

    private fun handleCustomButton2(context: Context) {
        Log.d(TAG, "Custom button 2 clicked")
        Toast.makeText(context, "Custom Button 2 clicked", Toast.LENGTH_SHORT).show()
    }

    private fun handleView(context: Context, intent: Intent) {
        Log.d(TAG, "View action clicked")
        Toast.makeText(context, "View action clicked", Toast.LENGTH_SHORT).show()
    }

    private fun handlePrevious(context: Context, intent: Intent) {
        Log.d(TAG, "Previous action clicked")
        Toast.makeText(context, "Previous action clicked", Toast.LENGTH_SHORT).show()
    }

    private fun handlePause(context: Context, intent: Intent) {
        Log.d(TAG, "Pause action clicked")
        Toast.makeText(context, "Pause action clicked", Toast.LENGTH_SHORT).show()
    }

    private fun handleNext(context: Context, intent: Intent) {
        Log.d(TAG, "Next action clicked")
        Toast.makeText(context, "Next action clicked", Toast.LENGTH_SHORT).show()
    }
}