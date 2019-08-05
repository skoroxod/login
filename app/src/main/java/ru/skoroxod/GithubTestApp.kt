package ru.skoroxod


import android.app.Application
import com.facebook.FacebookSdk
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import timber.log.Timber
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.skoroxod.github.GithubApiService




class GithubTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)

        VK.addTokenExpiredHandler(tokenTracker)
        Timber.plant(Timber.DebugTree())
        createRetrofit()
    }

    lateinit var githubApi: GithubApiService


    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
            // token expired
            Timber.tag("VK").d("Token expired")
        }
    }

    fun createRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        githubApi = retrofit.create(GithubApiService::class.java)
    }


}
