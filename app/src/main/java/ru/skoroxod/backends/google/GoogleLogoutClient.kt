package ru.skoroxod.backends.google

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import ru.skoroxod.backends.LogoutClient

class GoogleLogoutClient() : LogoutClient{

    private var gsOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    override fun logout(activity: Activity, onComplete: ((Unit) -> Unit)) {
        GoogleSignIn.getClient(activity, gsOptions).signOut().addOnCompleteListener(activity) {
            onComplete.invoke(Unit)
        }
    }

}