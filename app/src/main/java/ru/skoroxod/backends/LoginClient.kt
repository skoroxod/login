package ru.skoroxod.backends

import android.app.Activity
import android.content.Intent
import android.view.View

interface LoginClient {

    val onComplete: (BackendType, String) -> Unit

    val onError: (Exception) -> Unit


    fun processLogin(requestCode: Int, resultCode: Int, data: Intent?): Boolean

    fun initButton(button: View, activity: Activity)
}