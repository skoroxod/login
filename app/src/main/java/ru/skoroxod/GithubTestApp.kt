package ru.skoroxod


import android.app.Application
import com.facebook.FacebookSdk
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import timber.log.Timber


class GithubTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)

        VK.addTokenExpiredHandler(tokenTracker)
        Timber.plant(Timber.DebugTree())
    }

    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
            // token expired
            Timber.tag("VK").d("Token expired")
        }
    }


}
