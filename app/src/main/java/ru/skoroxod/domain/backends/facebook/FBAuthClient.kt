package ru.skoroxod.domain.backends.facebook

import android.content.Context
import com.facebook.AccessToken
import ru.skoroxod.domain.backends.AuthClient


class FBAuthClient : AuthClient {
    override fun isLoggedIn(context: Context): Boolean {
        AccessToken.getCurrentAccessToken()?.userId

        return AccessToken.getCurrentAccessToken() != null
    }
}