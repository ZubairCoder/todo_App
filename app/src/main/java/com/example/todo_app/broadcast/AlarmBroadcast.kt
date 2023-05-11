package com.example.todo_app.broadcast

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todo_app.R
import com.example.todo_app.db.Todo
import com.example.todo_app.ui.MainActivity
import com.example.viewmodel.ui.db.AppDatabase

class AlarmBroadcast : BroadcastReceiver() {
    private var mediaPlayer :MediaPlayer? = null
    private var todo :Todo?= null
    @SuppressLint("LaunchActivityFromNotification")
    override fun onReceive(context: Context, intent: Intent?) {
        val intent = Intent(context,MainActivity::class.java)
        intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context,"Notify_id")
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle(" WORK was schedule to do")
            .setContentText("Uth Oye chal kaam kr")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1,builder.build())
        mediaPlayer= MediaPlayer.create(context,Settings.System.DEFAULT_NOTIFICATION_URI)
        mediaPlayer?.start()



    }
}