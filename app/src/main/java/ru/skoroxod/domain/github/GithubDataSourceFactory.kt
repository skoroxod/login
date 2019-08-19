package ru.skoroxod.domain.github

import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class GithubDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val githubService: GithubApiService,
    private val getSearchQueryFunction: () -> String
) : DataSource.Factory<Long, GithubUser>() {

    lateinit var usersDataSourceLiveData: GithubDatasource

    override fun create(): DataSource<Long, GithubUser> {
        Timber.tag("DataSourceFactory").d("create new data source")
        val usersDataSource = GithubDatasource(githubService, compositeDisposable, getSearchQueryFunction.invoke())
        usersDataSourceLiveData = usersDataSource
        return usersDataSource
    }
}