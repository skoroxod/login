package ru.skoroxod.domain.backends

import android.app.Activity

interface LogoutClient {

    fun logout(activity: Activity, onComplete: (Unit) -> Unit)
}