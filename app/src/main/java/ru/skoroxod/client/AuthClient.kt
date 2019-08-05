package ru.skoroxod.client

import android.app.Activity
import android.content.Intent
import android.view.View
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.SignInButton
import ru.skoroxod.backends.facebook.FBLoginClient
import ru.skoroxod.backends.BackendType
import ru.skoroxod.backends.LoginClient
import ru.skoroxod.backends.google.GoogleLoginClient
import ru.skoroxod.backends.vk.VKLoginClient

class AuthClient(
                  val onSuccess: ((BackendType, String) -> Unit),
                  val onError: ((Exception) -> Unit)
) {

    private var backends: Map<BackendType, LoginClient> = mapOf(
            BackendType.VK to VKLoginClient(onSuccess, onError),
            BackendType.GOOGLE to GoogleLoginClient(onSuccess, onError),
            BackendType.FACEBOOK to FBLoginClient(onSuccess, onError)
    )

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        backends.values.forEach { it.processLogin(requestCode, resultCode, data) }
    }

    fun initButton(button: View, activity: Activity) {
        when (button) {
            is SignInButton -> { //google
                backends[BackendType.GOOGLE]?.initButton(button, activity)
            }
            is LoginButton -> { // FB
                backends[BackendType.FACEBOOK]?.initButton(button, activity)
            }
            else -> {  //VK
                backends[BackendType.VK]?.initButton(button, activity)
            }
        }

    }
}

