package ru.skoroxod.domain.backends.vk

import android.app.Activity
import com.vk.api.sdk.VK
import ru.skoroxod.domain.backends.LogoutClient

class VKLogoutClient: LogoutClient {

    override fun logout(activity: Activity, onComplete: (Unit) -> Unit) {
        VK.logout()
        onComplete.invoke(Unit)
    }
}