package ru.skoroxod.github


data class GithubResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<GithubUser>
)


data class GithubUser(
    val login: String,
    val id: Long,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val starred_url: String,
    val gists_url: String,
    val type: String,
    val score: Float
)
