package ru.skoroxod.repo

import io.reactivex.Single
import ru.skoroxod.backends.BackendType

class UserRepo {

    var userInfo: UserInfo? = null

    var currentBackend: BackendType? = null


    fun login(backendType: BackendType, userInfo: UserInfo) {
        currentBackend = backendType
        this.userInfo = userInfo

    }
    fun logout() {
        userInfo = null
        currentBackend = null
    }
}


data class UserInfo(val userId: String, val displayName: String, val email: String?, val imageUrl: String)

