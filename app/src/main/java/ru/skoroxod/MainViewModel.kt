package ru.skoroxod

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vk.api.sdk.VK
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.skoroxod.backends.BackendType
import ru.skoroxod.backends.vk.VKUserRequest
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
            userRepo.currentBackend = loggedInBackendType
            viewState.postValue(ViewState.LoggedIn(loggedInBackendType.name))
        } else {
            Timber.tag("ViewModel").d("not logged in")
            viewState.postValue(ViewState.NotLoggedIn())
        }
    }

    fun onLoginComplete(backendType: BackendType) {
        userRepo.currentBackend = backendType
        viewState.postValue(ViewState.LoggedIn("loggedIn"))

        val d = Observable.fromCallable {
            VK.executeSync(VKUserRequest(183462871))
        }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // response here
            }, {
                Timber.tag("ViewModel").d("error: $it")
                // throwable here
            })
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
}

sealed class ViewState {
    class NotLoggedIn : ViewState()

    class LoggedIn(val user: String) : ViewState()

}