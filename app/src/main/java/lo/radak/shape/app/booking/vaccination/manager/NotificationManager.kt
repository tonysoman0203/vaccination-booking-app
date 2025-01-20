package lo.radak.shape.app.booking.vaccination.manager

import android.R.*
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import lo.radak.shape.app.booking.vaccination.MainActivity
import lo.radak.shape.app.booking.vaccination.MyNotificationPublisher
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.ui.Booking.ThankYou.BookingResponseFragment

import android.app.NotificationChannel
import android.R.attr.description
import android.graphics.Color


object NotificationManager {
    const val NOTIFICATION_CHANNEL_ID = "10001"
    private const val default_notification_channel_id = "default"
    var notificationID = 0

    fun getNotification(
        context: Context,
        intent: Intent,
        content: String
    ): Notification? {
        val builder = NotificationCompat.Builder(context, default_notification_channel_id)
        val notiManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        var mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, default_notification_channel_id, importance)
        mChannel.setDescription("Booking")
        mChannel.enableVibration(true)
        mChannel.lightColor = Color.GREEN
        mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notiManager.createNotificationChannel(mChannel)

        builder.setContentTitle("Booking")
        builder.setContentText(content)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
            .setWhen(0)
            .priority = NotificationCompat.PRIORITY_HIGH


        val activity = PendingIntent.getActivity(
            context,
            1,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        builder.setContentIntent(activity)
        return builder.build()
    }

    fun scheduleNotification(  context: Context, notification: Notification, delay: Long) {
        val notificationIntent = Intent(context, MyNotificationPublisher::class.java)
        notificationID++
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationID)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        Log.d(BookingResponseFragment.TAG, "scheduleNotification futureInMillis = $delay")
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager;
        alarmManager[AlarmManager.RTC_WAKEUP, delay] = pendingIntent
    }
}