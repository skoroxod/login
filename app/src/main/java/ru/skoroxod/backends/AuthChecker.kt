package ru.skoroxod.backends

import android.content.Context

interface AuthChecker {
    fun isLoggedIn(context: Context): Boolean
}