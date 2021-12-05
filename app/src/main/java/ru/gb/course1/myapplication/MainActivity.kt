package ru.gb.course1.myapplication

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import ru.gb.course1.myapplication.databinding.ActivityMainBinding
import java.util.*

private const val TEST_NOTIFICATION_ID = 123
private const val TEST_CHANNEL_ID = "TEST_CHANNEL_ID"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = NotificationManagerCompat.from(this)

        NotificationManagerCompat.from(this)

        createChannelsOnStart(notificationManager)

        binding.showNotificationButton.setOnClickListener {
            notificationManager.notify(TEST_NOTIFICATION_ID, createNotification(this))
        }
    }
}

private fun createChannelsOnStart(notificationManager: NotificationManagerCompat) {
    val channel = NotificationChannelCompat.Builder(
        TEST_CHANNEL_ID,
        NotificationManagerCompat.IMPORTANCE_HIGH
    )
        .setName("Наш первый канал")
        .setDescription("Описание зачем вообще этот канал и что тут будет")
        .build()
    notificationManager.createNotificationChannel(channel)
}

private fun createNotification(context: Context): Notification {
    val icon = AppCompatResources.getDrawable(context, R.drawable.ic_4k_vector)
    val bitmap = icon?.toBitmap(200, 200)

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

    return NotificationCompat.Builder(context, TEST_CHANNEL_ID)
        .setContentTitle("Привет, я заголовок")
        .setContentText(UUID.randomUUID().toString())
        .setLargeIcon(bitmap)
        .setColor(Color.RED)
        .setColorized(true)
        .addAction(R.drawable.ic_test_notification, "Нажми меня", pendingIntent)
        .setStyle(
            NotificationCompat.InboxStyle()
                .addLine("ololo")
                .addLine("lalala")
                .setBigContentTitle("FKFKFKFK")
        )
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_test_notification)
        .build()
}