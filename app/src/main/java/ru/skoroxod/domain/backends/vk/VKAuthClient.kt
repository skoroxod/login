package ru.skoroxod.domain.backends.vk

import android.content.Context
import com.vk.api.sdk.VK
import ru.skoroxod.domain.backends.AuthClient

class VKAuthClient : AuthClient {
    override fun isLoggedIn(context: Context): Boolean {
        return VK.isLoggedIn()
    }
}