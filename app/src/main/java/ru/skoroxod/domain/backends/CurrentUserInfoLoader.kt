package ru.skoroxod.domain.backends

import io.reactivex.Single
import ru.skoroxod.domain.repo.UserInfo

interface CurrentUserInfoLoader {

    fun loadCurrentUserInfo(): Single<UserInfo>
}