package ru.skoroxod.github

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber

class GithubDatasource(
    private val githubService: GithubApiService,
    private val compositeDisposable: CompositeDisposable,
    private val searchQuery: String
) : PageKeyedDataSource<Long, GithubUser>() {



    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, GithubUser>) {

        githubService
            .searchUsers(searchQuery, 1, params.requestedLoadSize)
            .subscribe(
                { users ->
                    Timber.tag("DataSource").d("loaded. page 1, ${params.requestedLoadSize}")
                    callback.onResult(users.items, 1,  2)
                },
                {
                    Timber.tag("DataSource").e("loadInitial error : $it")
                }
            )
            .addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, GithubUser>) {
        githubService
            .searchUsers(searchQuery, params.key.toInt(), params.requestedLoadSize)
            .subscribe(
                { users ->
                    Timber.tag("DataSource").d("loaded. page ${params.key.toInt()}, ${params.requestedLoadSize}")
                    callback.onResult(users.items,params.key + 1)
                },
                {
                    Timber.tag("DataSource").e("loadAfter page ${params.key.toInt()}, ${params.requestedLoadSize}. error : $it")
                }
            ).addTo(compositeDisposable)

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, GithubUser>) {
    }
}