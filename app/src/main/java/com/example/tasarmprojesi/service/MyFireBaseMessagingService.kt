package com.example.tasarmprojesi.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.tasarmprojesi.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {//Bildirim geldiğinde çağırılacak.
        remoteMessage.notification?.let {//Gelen bildirimin içeriği kontrol ediliyor.
            val title = it.title
            val body = it.body

            val notificationManager = ContextCompat.getSystemService(//Bildirim yöneticisi oluşturulur.
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager

            val notificationChannel = NotificationChannel(//Bildirim Kanalı oluşturulur.
                "channel_id",
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT//Bildirimin önem derecesi default olarak belirlendi.
            )

            notificationManager.createNotificationChannel(notificationChannel)//Bildirim yöneticiyle oluşturulan kanal belirtildi.

            //Bildirim oluşturulur ve özellikleri belirlenir.
            val notificationBuilder = NotificationCompat.Builder(applicationContext, "channel_id")
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//Bildirim öncelik düzeyi default olarak belirlendi.
                .setChannelId("channel_id")

            notificationManager.notify(0, notificationBuilder.build())//Bildirim gösterilir.
        }

    }
}