package ru.skoroxod.domain.backends.facebook

import android.app.Activity
import com.facebook.login.LoginManager
import ru.skoroxod.domain.backends.LogoutClient

class FBLogoutClient : LogoutClient {

    override fun logout(activity: Activity, onComplete: (Unit) -> Unit) {
        LoginManager.getInstance().logOut()
        onComplete.invoke(Unit)
    }
}