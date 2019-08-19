package ru.skoroxod.domain.backends

import android.content.Context


/**
 * Класс для проверки существующей аутентификации
 *
 */
class LoginChecker {

    /**
     * возвращает BackendType если пользователь залогинен в данном backend'е
     * или null если пользователь не залогиннен ни в одном из backend'ов
     */
    fun isLoggedIn(context: Context): BackendType? {
        return BackendFactory().getAllAuthClients().entries.find { b -> b.value.isLoggedIn(context) }?.key
    }
}