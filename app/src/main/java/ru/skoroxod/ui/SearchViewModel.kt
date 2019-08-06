package ru.skoroxod.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import ru.skoroxod.github.GithubApiService
import ru.skoroxod.github.GithubDataSourceFactory
import ru.skoroxod.github.GithubUser
import timber.log.Timber

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private var config: PagedList.Config
    val disposables = CompositeDisposable()


   var userList: LiveData<PagedList<GithubUser>>? = null


    private val pageSize = 15

//    private val sourceFactory: GithubDataSourceFactory

    init {
//        sourceFactory = GithubDataSourceFactory(disposables, GithubApiService.getService())

        config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()

//        userList = LivePagedListBuilder<Long, GithubUser>(sourceFactory, config).build()

    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun search(query: String) {
        Timber.tag("query").d(" $query")
        if (query.isNullOrEmpty()) {
            Timber.tag("data").d(" empty query")
            return
        }
        val sourceFactory = GithubDataSourceFactory(disposables, GithubApiService.getService(),query)
//        sourceFactory.setQuery(query)

        userList = LivePagedListBuilder<Long, GithubUser>(sourceFactory, config).build()
    }

}