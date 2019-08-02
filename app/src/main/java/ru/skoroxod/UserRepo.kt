package ru.skoroxod

import io.reactivex.Single
import ru.skoroxod.backends.BackendType

class UserRepo {

    var userInfo: UserInfo? = null

    var currentBackend: BackendType? = null




    fun getUser(): Single<UserInfo>? {
            return Single.just(userInfo)
    }

    fun login() {

    }
    fun logout() {
        userInfo = null
        currentBackend = null
    }
}


data class UserInfo(val firstName: String, val lastName: String?, val imageUrl: String)

