package ru.skoroxod.github

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable

class GithubDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val githubService: GithubApiService,
    private val query: String
) : DataSource.Factory<Long, GithubUser>() {

    val usersDataSourceLiveData = MutableLiveData<GithubDatasource>()

    override fun create(): DataSource<Long, GithubUser> {
        val usersDataSource = GithubDatasource(githubService, compositeDisposable, query)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }
}