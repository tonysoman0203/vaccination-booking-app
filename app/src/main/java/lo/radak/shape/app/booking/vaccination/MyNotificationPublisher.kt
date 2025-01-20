package lo.radak.shape.app.booking.vaccination

import android.app.Notification
import android.app.NotificationChannel

import android.app.NotificationManager

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import lo.radak.shape.app.booking.vaccination.manager.NotificationManager.NOTIFICATION_CHANNEL_ID


class MyNotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification? = intent.getParcelableExtra(NOTIFICATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_NAME,
                importance
            )
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        assert(notificationManager != null)
        notificationManager.notify(id, notification)
    }

    companion object {
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
        val NOTIFICATION_NAME = "Vaccination Booking"
    }
}
