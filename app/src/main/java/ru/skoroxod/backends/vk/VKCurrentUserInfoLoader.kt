package ru.skoroxod.backends.vk

import com.vk.api.sdk.VK
import io.reactivex.Single
import ru.skoroxod.backends.CurrentUserInfoLoader
import ru.skoroxod.repo.UserInfo

class VKCurrentUserInfoLoader : CurrentUserInfoLoader {

    override fun loadCurrentUserInfo(): Single<UserInfo> {
        return Single.fromCallable {
            VK.executeSync(VKCurrentUserRequest())
        }.map { VKUser.toUserInfo(it) }
    }

}