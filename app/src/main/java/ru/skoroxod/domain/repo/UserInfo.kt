package ru.skoroxod.domain.repo


/**
 * Информация о пользователе.
 * Общаця для всех используемых backend'ов
 */

data class UserInfo(val userId: String, val displayName: String, val email: String?, val imageUrl: String)