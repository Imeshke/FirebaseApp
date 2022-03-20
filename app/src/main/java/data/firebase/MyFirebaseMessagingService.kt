package com.imeshke.firebaselogin.data.firebase

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.imeshke.firebaselogin.R


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleMessage(remoteMessage)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {

        val handler = Handler(Looper.getMainLooper())

        handler.post(Runnable {
            Toast.makeText(baseContext, getString(R.string.handle_notification_now),
                Toast.LENGTH_LONG).show()
            remoteMessage.notification?.let {
                val intent = Intent("MyData")
                intent.putExtra("message",remoteMessage.data["text"]);
                broadcaster?.sendBroadcast(intent);
            }
        }
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO : send token to tour server
    }

}
