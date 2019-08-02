package ru.skoroxod.backends

import android.app.Activity

interface LogoutClient {

    fun logout(activity: Activity, onComplete: (Unit) -> Unit)
}