package ru.skoroxod.backends

import ru.skoroxod.backends.facebook.FBLoginClient
import ru.skoroxod.backends.facebook.FBAuthChecker
import ru.skoroxod.backends.facebook.FBLogoutClient
import ru.skoroxod.backends.google.GoogleAuthChecker
import ru.skoroxod.backends.google.GoogleLoginClient
import ru.skoroxod.backends.google.GoogleLogoutClient
import ru.skoroxod.backends.vk.VKAuthChecker
import ru.skoroxod.backends.vk.VKLoginClient
import ru.skoroxod.backends.vk.VKLogoutClient

class BackendFactory {

    fun getAllLoginChecker(): Map<BackendType, AuthChecker> {
        return authCheckers
    }

    fun getLoginChecker(backendType: BackendType): AuthChecker {
        return authCheckers[backendType]!!
    }

    fun getLogoutClient(backendType: BackendType): LogoutClient {
        return when (backendType) {
            BackendType.VK -> VKLogoutClient()
            BackendType.GOOGLE -> GoogleLogoutClient()
            BackendType.FACEBOOK -> FBLogoutClient()
        }
    }

    fun getLoginClient(backendType: BackendType, onComplete: (BackendType) -> Unit, onError: (Exception) -> Unit): LoginClient {
        return when (backendType) {
            BackendType.VK -> VKLoginClient(onComplete, onError)
            BackendType.GOOGLE -> GoogleLoginClient(onComplete, onError)
            BackendType.FACEBOOK -> FBLoginClient(onComplete, onError)
        }

    }

    companion object {
        val authCheckers = mapOf(
            BackendType.FACEBOOK to FBAuthChecker(),
            BackendType.VK to VKAuthChecker(),
            BackendType.GOOGLE to GoogleAuthChecker()
        )
    }
}