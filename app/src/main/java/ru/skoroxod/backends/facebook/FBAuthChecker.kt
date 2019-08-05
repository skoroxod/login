package ru.skoroxod.backends.facebook

import android.content.Context
import com.facebook.AccessToken
import ru.skoroxod.backends.AuthChecker


class FBAuthChecker : AuthChecker {
    override fun isLoggedIn(context: Context): Boolean {
        AccessToken.getCurrentAccessToken()?.userId

        return AccessToken.getCurrentAccessToken() != null
    }
}