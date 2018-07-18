package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rviannaoliveira.vformmvvm.model.LoginInfo
import com.rviannaoliveira.vformmvvm.model.LoginViewState
import com.rviannaoliveira.vformmvvm.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val viewState: BehaviorSubject<LoginViewState> = BehaviorSubject.create()
    private val disposableView = CompositeDisposable()
    private var loginViewState = LoginViewState()

    fun expectedResult(): Observable<LoginViewState> =
            viewState.serialize()

    fun bindView(loginView: ILoginView, formValidator: LoginValidator) {
        disposableView.add(loginView
                .startLogin()
                .subscribe {
                    loginViewState = loginViewState.copy(showProgress = true)
                    viewState.onNext(loginViewState)
                    authenticateUser(it)
                })

        disposableView.add(loginView.emailFilled().subscribe {
            val emailValid = formValidator.isEmailValid(it)
            loginViewState = loginViewState.copy(emailErrorMessage = emailValid)
            viewState.onNext(loginViewState)
            formValidator.emailValidSubject.onNext(emailValid.isEmpty())
        })
        disposableView.add(loginView.passwordFilled().subscribe {
            val passwordValid = formValidator.isPasswordValid(it)
            loginViewState = loginViewState.copy(passwordErrorMessage = passwordValid)
            viewState.onNext(loginViewState)
            formValidator.passwordValidSubject.onNext(passwordValid.isEmpty())
        })

        disposableView.add(formValidator.isValid().subscribe {
            loginViewState = loginViewState.copy(enableSubmit = it)
            viewState.onNext(loginViewState)
        })
    }

    private fun authenticateUser(loginInfo: LoginInfo) {
        val loginCredential = User(loginInfo.email, loginInfo.password)

        repository.login(loginCredential)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loginViewState = loginViewState.copy(isUserLogged = true, showProgress = false)
                    viewState.onNext(loginViewState)
                }, {
                    loginViewState = loginViewState.copy(isError = true, showProgress = false)
                    viewState.onNext(loginViewState)
                })
    }

    fun unbindView() {
        disposableView.clear()
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(LoginRepository()) as T
        }
    }
}
