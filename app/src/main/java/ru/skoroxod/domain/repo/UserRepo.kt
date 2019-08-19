package ru.skoroxod.domain.repo

import ru.skoroxod.domain.backends.BackendType



/**
 * Repository  для хранение информации о пользователе.
 */
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


