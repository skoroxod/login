package ru.skoroxod.backends.vk

import android.content.Context
import com.vk.api.sdk.VK
import ru.skoroxod.backends.AuthChecker

class VKAuthChecker : AuthChecker {
    override fun isLoggedIn(context: Context): Boolean {
        return VK.isLoggedIn()
    }
}