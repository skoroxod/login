package ru.skoroxod.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.skoroxod.domain.backends.LoginChecker
import ru.skoroxod.domain.backends.BackendFactory
import ru.skoroxod.domain.backends.BackendType
import ru.skoroxod.domain.repo.UserRepo
import timber.log.Timber


/**
 * ViewModel для MainActivity.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    val viewState = MutableLiveData<MainActivtiyViewState>()

    val userRepo = UserRepo()

    val disposables: CompositeDisposable = CompositeDisposable()

    fun isLoggedIn() {
        Timber.tag("ViewModel").d("is logged in")

        val loggedInBackendType = LoginChecker().isLoggedIn(this.getApplication())

        if (loggedInBackendType != null) {
            Timber.tag("ViewModel").d("loggedin with : ${loggedInBackendType.name}")
            loadUserInfo(loggedInBackendType)

        } else {
            Timber.tag("ViewModel").d("not logged in")
            viewState.postValue(MainActivtiyViewState.NotLoggedIn())
        }
    }

    fun onLoginComplete(backendType: BackendType, userId: String) {
        userRepo.currentBackend = backendType

        Timber.tag("ViewModel.login").d("complete. $backendType, $userId")
        loadUserInfo(backendType)
    }

    fun onLoginError(error: Exception) {
        Timber.tag("ViewModel.login").e("error: ${error.message}")
    }

    fun logout() {
        Timber.tag("ViewModel.logout").d("logout")

        userRepo.logout()
        viewState.postValue(MainActivtiyViewState.NotLoggedIn())
    }

    fun getCurrentBackend(): BackendType? {
        return userRepo.currentBackend
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun loadUserInfo(backendType: BackendType) {
        BackendFactory().getUserInfoLoader(backendType, getApplication()).loadCurrentUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    userRepo.login(backendType, it)
                    viewState.postValue(MainActivtiyViewState.LoggedIn(it))
                },
                {
                    Timber.tag("ViewModel.login").e("error in load user info. $it")
                }
            ).addTo(disposables)
    }
}

