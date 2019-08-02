package ru.skoroxod.backends.vk

import android.app.Activity
import com.vk.api.sdk.VK
import ru.skoroxod.backends.LogoutClient

class VKLogoutClient: LogoutClient {

    override fun logout(activity: Activity, onComplete: (Unit) -> Unit) {
        VK.logout()
        onComplete.invoke(Unit)
    }
}