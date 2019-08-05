package ru.skoroxod.github

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<GithubResponse>
}