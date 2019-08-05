package ru.skoroxod.backends.facebook

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import io.reactivex.Single
import ru.skoroxod.backends.CurrentUserInfoLoader
import ru.skoroxod.repo.UserInfo
import timber.log.Timber


class FBCurrentUserInfoLoader : CurrentUserInfoLoader {

    override fun loadCurrentUserInfo(): Single<UserInfo> {
        return Single.defer {
            val token = AccessToken.getCurrentAccessToken()
            val request = GraphRequest.newMeRequest(token) { _,_ -> }
            val parameters = Bundle()
            parameters.putString("fields", "id,name")
            request.parameters = parameters
            val response = request.executeAndWait()

            Timber.tag("facebook").d(response.jsonObject.toString(4))

            val id = response.jsonObject.getString("id")
            val name = response.jsonObject.getString("name")
            val url = "https://graph.facebook.com/v4.0/$id/picture"
            Single.just(UserInfo(id, name, "", url))
        }
    }
}