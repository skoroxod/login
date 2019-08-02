package ru.skoroxod

import android.content.Context
import ru.skoroxod.backends.BackendFactory
import ru.skoroxod.backends.BackendType

class LoginChecker {

    fun isLoggedIn(context: Context): BackendType? {
        return BackendFactory().getAllLoginChecker().entries.find { b -> b.value.isLoggedIn(context) }?.key
    }
}