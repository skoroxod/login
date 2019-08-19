package ru.skoroxod.domain.backends.vk

import android.app.Activity
import android.content.Intent
import android.view.View
import ru.skoroxod.domain.backends.LoginClient
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import ru.skoroxod.domain.backends.BackendType
import timber.log.Timber

class VKLoginClient(override val onComplete: (BackendType, String) -> Unit, override val onError: (Exception) -> Unit) :
    LoginClient {

    private val callback = object : VKAuthCallback {
        override fun onLogin(token: VKAccessToken) {
            Timber.d("user id: ${token.userId}")
            onComplete.invoke(BackendType.VK, token.userId?.toString()?: "null")
        }

        override fun onLoginFailed(errorCode: Int) {
            onError.invoke(Exception("Error in VK login. Code: $errorCode"))
        }
    }

    override fun initButton(button: View, activity: Activity) {
        button.setOnClickListener {
            VK.login(activity, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }
    }

    override fun processLogin(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return !VK.onActivityResult(requestCode, resultCode, data, callback)
    }
}