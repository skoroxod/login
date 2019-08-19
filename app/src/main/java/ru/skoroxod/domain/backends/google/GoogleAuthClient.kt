package ru.skoroxod.domain.backends.google

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import ru.skoroxod.domain.backends.AuthClient

class GoogleAuthClient : AuthClient {

    override fun isLoggedIn(context: Context): Boolean {
        return GoogleSignIn.getLastSignedInAccount(context) != null
    }
}