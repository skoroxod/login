package ru.skoroxod.domain.backends

import android.content.Context
import ru.skoroxod.domain.backends.facebook.FBLoginClient
import ru.skoroxod.domain.backends.facebook.FBAuthClient
import ru.skoroxod.domain.backends.facebook.FBLogoutClient
import ru.skoroxod.domain.backends.facebook.FBCurrentUserInfoLoader
import ru.skoroxod.domain.backends.google.GoogleAuthClient
import ru.skoroxod.domain.backends.google.GoogleLoginClient
import ru.skoroxod.domain.backends.google.GoogleLogoutClient
import ru.skoroxod.domain.backends.google.GoogleCurrentUserInfoLoader
import ru.skoroxod.domain.backends.vk.VKAuthClient
import ru.skoroxod.domain.backends.vk.VKLoginClient
import ru.skoroxod.domain.backends.vk.VKLogoutClient
import ru.skoroxod.domain.backends.vk.VKCurrentUserInfoLoader
import kotlin.collections.Map as Map

class BackendFactory {

    fun getAllAuthClients(): Map<BackendType, AuthClient> {
        return mapOf(
            BackendType.FACEBOOK to FBAuthClient(),
            BackendType.VK to VKAuthClient(),
            BackendType.GOOGLE to GoogleAuthClient()
        )
    }

    fun getLogoutClient(backendType: BackendType): LogoutClient {
        return when (backendType) {
            BackendType.VK -> VKLogoutClient()
            BackendType.GOOGLE -> GoogleLogoutClient()
            BackendType.FACEBOOK -> FBLogoutClient()
        }
    }

    fun getUserInfoLoader(backendType: BackendType, context: Context): CurrentUserInfoLoader {
        return when (backendType) {
            BackendType.VK -> VKCurrentUserInfoLoader()
            BackendType.GOOGLE -> GoogleCurrentUserInfoLoader(context)
            BackendType.FACEBOOK -> FBCurrentUserInfoLoader()
        }
    }

    fun getLoginClients(
        onSuccess: ((BackendType, String) -> Unit),
        onError: ((Exception) -> Unit)
    ): Map<BackendType, LoginClient> {
        return mapOf(
            BackendType.VK to VKLoginClient(onSuccess, onError),
            BackendType.GOOGLE to GoogleLoginClient(onSuccess, onError),
            BackendType.FACEBOOK to FBLoginClient(onSuccess, onError)
        )
    }
}