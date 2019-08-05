package ru.skoroxod

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchViewModel(application: Application): AndroidViewModel(application) {

    val disposables = CompositeDisposable()

    fun search(query: String) {
        if (query.isNullOrEmpty()) {
            Timber.tag("data").d(" empty query")
            return
        }
        getApplication<GithubTestApp>().githubApi.searchUsers(query, 1, 10)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Timber.tag("data").d("response ${it.items}")
                    //viewState.postValue(ViewState.LoggedIn(userRepo.userInfo!!, it.items))
                },
                {
                    Timber.tag("data").e(it)
                }
            ).addTo(disposables)
    }

}