package ru.skoroxod.backends

import io.reactivex.Single
import ru.skoroxod.repo.UserInfo

interface CurrentUserInfoLoader {

    fun loadCurrentUserInfo(): Single<UserInfo>
}