package ru.skoroxod

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.skoroxod.backends.BackendFactory
import ru.skoroxod.backends.BackendType
import ru.skoroxod.github.GithubUser
import ru.skoroxod.repo.UserInfo
import ru.skoroxod.repo.UserRepo
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val viewState = MutableLiveData<ViewState>()

    private val loginChecker = LoginChecker()

    val userRepo = UserRepo()

    val disposables: CompositeDisposable = CompositeDisposable()

    fun isLoggedIn() {
        Timber.tag("ViewModel").d("is logged in")

        val loggedInBackendType = loginChecker.isLoggedIn(this.getApplication())

        if (loggedInBackendType != null) {
            Timber.tag("ViewModel").d("loggedin with : ${loggedInBackendType.name}")
            loadUserInfo(loggedInBackendType)

        } else {
            Timber.tag("ViewModel").d("not logged in")
            viewState.postValue(ViewState.NotLoggedIn())
        }
    }

    fun onLoginComplete(backendType: BackendType, userId: String) {
        userRepo.currentBackend = backendType

        Timber.tag("ViewModel.login").d("complete. $backendType, $userId")
        loadUserInfo(backendType)

    }

    fun onLoginError(error: Exception) {
    }

    fun logout() {
        Timber.tag("ViewModel.logout").d("logout")

        userRepo.logout()
        viewState.postValue(ViewState.NotLoggedIn())
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
                    viewState.postValue(ViewState.LoggedIn(it))
                },
                {
                    Timber.tag("ViewModel.login").e("error in load user info. $it")
                }
            ).addTo(disposables)
    }


}

sealed class ViewState {
    class NotLoggedIn : ViewState()

    class LoggedIn(val userInfo: UserInfo, val users: List<GithubUser> = listOf()) : ViewState()

}