package ru.skoroxod.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import ru.skoroxod.domain.github.GithubApiService
import ru.skoroxod.domain.github.GithubDataSourceFactory
import ru.skoroxod.domain.github.GithubUser
import timber.log.Timber

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private var sourceFactory: GithubDataSourceFactory

    private var searchQuery: String = ""

    val disposables = CompositeDisposable()

    var userList: LiveData<PagedList<GithubUser>>? = null

    private val pageSize = 30

    init {
        sourceFactory = GithubDataSourceFactory(disposables, GithubApiService.getService(), { searchQuery } )

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()

        userList = LivePagedListBuilder<Long, GithubUser>(sourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun search(query: String) {
        Timber.tag("query").d(" $query")
        searchQuery = query
        sourceFactory.usersDataSourceLiveData.invalidate()
    }

}