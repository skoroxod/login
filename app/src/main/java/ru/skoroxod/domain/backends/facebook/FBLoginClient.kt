package ru.skoroxod.domain.backends.facebook

import android.app.Activity
import android.content.Intent
import android.view.View
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import ru.skoroxod.domain.backends.BackendType
import ru.skoroxod.domain.backends.LoginClient
import java.util.*

class FBLoginClient(
    override val onComplete: (BackendType, String) -> Unit,
    override val onError: (Exception) -> Unit
) :
    LoginClient {


    val callbackManager: CallbackManager = CallbackManager.Factory.create()


    override fun initButton(button: View, activity: Activity) {

        (button as LoginButton).apply {

            setReadPermissions(Arrays.asList("email"))
            setAuthType("rerequest")

            registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {

                    onComplete.invoke(BackendType.FACEBOOK, loginResult.accessToken.userId)
                }

                override fun onCancel() {
                }

                override fun onError(exception: FacebookException) {
                    onError.invoke(exception)
                }
            })
        }

    }

    override fun processLogin(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}