package ru.skoroxod.domain.backends.google

import android.app.Activity
import android.content.Intent
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import ru.skoroxod.domain.backends.BackendType
import ru.skoroxod.domain.backends.LoginClient

class GoogleLoginClient(
    override val onComplete: (BackendType, String) -> Unit,
    override val onError: (Exception) -> Unit
) : LoginClient {

    override fun initButton(button: View, activity: Activity) {

        val googleSignInClient = GoogleSignIn.getClient(activity, gsOptions)
        val signInButton = button as SignInButton
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener {
            activity.startActivityForResult(googleSignInClient.signInIntent, REQUEST_CODE)
        }

    }

    private var gsOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()


    override fun processLogin(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == REQUEST_CODE) {
            val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = completedTask.getResult(ApiException::class.java)
                if (account != null) {
                    onComplete.invoke(BackendType.GOOGLE, account.id ?: "null")
                } else {
                    onError.invoke(ApiException(Status(0, "account is null")))
                }
            } catch (e: ApiException) {
                onError.invoke(e)
            }
            return true
        }
        return false
    }

    companion object {
        const val REQUEST_CODE = 0x123
    }
}