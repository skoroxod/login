package ru.skoroxod.backends.google

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import ru.skoroxod.backends.AuthChecker

class GoogleAuthChecker : AuthChecker {

    override fun isLoggedIn(context: Context): Boolean {
        return GoogleSignIn.getLastSignedInAccount(context) != null
    }
}