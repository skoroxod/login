package ru.skoroxod.backends.facebook

import android.app.Activity
import com.facebook.login.LoginManager
import ru.skoroxod.backends.LogoutClient

class FBLogoutClient : LogoutClient {

    override fun logout(activity: Activity, onComplete: (Unit) -> Unit) {
        LoginManager.getInstance().logOut()
        onComplete.invoke(Unit)
    }
}