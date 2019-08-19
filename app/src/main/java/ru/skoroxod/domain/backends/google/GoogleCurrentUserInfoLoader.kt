package ru.skoroxod.domain.backends.google

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import io.reactivex.Single
import ru.skoroxod.domain.backends.CurrentUserInfoLoader
import ru.skoroxod.domain.repo.UserInfo

class GoogleCurrentUserInfoLoader(val context: Context) : CurrentUserInfoLoader {

    override fun loadCurrentUserInfo(): Single<UserInfo> {

        return Single.defer {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            if (account == null) {
                Single.error(Exception("User not logged in"))
            } else {
                Single.just(
                    UserInfo(
                        account.id ?: "null",
                        account.displayName ?: "Unknown user",
                        account.email,
                        account.photoUrl.toString()
                    )
                )
            }
        }
    }
}