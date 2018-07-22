package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.rviannaoliveira.vformmvvm.model.LoginInfo
import com.rviannaoliveira.vformmvvm.model.LoginViewState
import com.rviannaoliveira.vformmvvm.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
class LoginViewModel(private val repository: LoginRepository,
                     private val loginValidator: LoginValidator) : ViewModel() {

    private var loginViewState = LoginViewState()
    private val disposable = CompositeDisposable()
    val emailValidator = loginValidator.emailError
    val passwordValidator = loginValidator.passwordError
    val enableButton = loginValidator.enableButton
    val emailObserver = MutableLiveData<LoginViewState>()
    val passwordObserver = MutableLiveData<LoginViewState>()
    lateinit var submitObserver : MutableLiveData<LoginViewState>

    fun authenticateUser(loginInfo: LoginInfo) {
        val user = User(loginInfo.email, loginInfo.password)
        repository.login(user)
                .doOnSubscribe {
                    loginViewState = loginViewState.copy(showProgress = true)
                    submitObserver.postValue(loginViewState)
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    expectResult(isUserLogged = it)
                }, {
                    expectResult(isError = true)
                })
    }

    private fun expectResult(isUserLogged : Boolean = false, isError : Boolean = false) {
        submitObserver.value = loginViewState.copy(isUserLogged = isUserLogged, showProgress = false,isError = isError)
    }

    fun validEmail(text: String) {
        loginViewState = loginViewState.copy(email = text)
        loginValidator.isEmailValid(loginViewState)
    }

    fun validPassword(text: String){
        loginViewState = loginViewState.copy(password = text)
        loginValidator.isPasswordValid(loginViewState)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    class Factory(private val requireContext: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(LoginRepository(),LoginValidator(requireContext)) as T
        }
    }
}

