package ru.skoroxod.domain.backends

import android.content.Context

interface AuthClient {
    fun isLoggedIn(context: Context): Boolean
}