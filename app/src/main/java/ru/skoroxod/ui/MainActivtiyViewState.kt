package ru.skoroxod.ui

import ru.skoroxod.domain.repo.UserInfo


/**
 * ViewState используемый в MainActivity
 */
sealed class MainActivtiyViewState {

    /**
     * State - Пользователь не авторизован
     */
    class NotLoggedIn : MainActivtiyViewState()

    /**
     * State - Пользователь авторизован
     */
    class LoggedIn(val userInfo: UserInfo) : MainActivtiyViewState()
}