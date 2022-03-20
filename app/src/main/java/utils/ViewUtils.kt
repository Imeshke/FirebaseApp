package com.imeshke.firebaselogin.utils

import android.content.Context
import android.content.Intent
import com.imeshke.firebaselogin.api.model.Hotel
import com.imeshke.firebaselogin.ui.auth.LoginActivity
import com.imeshke.firebaselogin.ui.main.DetailActivity
import com.imeshke.firebaselogin.ui.main.HotelsActivity


fun Context.startHomeActivity() =
    Intent(this, HotelsActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }


fun Context.startDetailActivity(hotel:Hotel) {
    val intent = Intent(this, DetailActivity::class.java)
    intent.putExtra("HOTEL", hotel)
    startActivity(intent)
}