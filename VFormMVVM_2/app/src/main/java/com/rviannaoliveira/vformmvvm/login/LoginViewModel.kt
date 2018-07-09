package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.rviannaoliveira.vformmvvm.model.LoginInfo
import com.rviannaoliveira.vformmvvm.model.LoginViewState
import com.rviannaoliveira.vformmvvm.model.User
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class LoginViewModel(private val repository: LoginRepository,
                     private val formValidator: LoginValidator) : ViewModel() {

    private val viewState: BehaviorSubject<LoginViewState> = BehaviorSubject.create()
    private val disposable = CompositeDisposable()

    fun expectedResult(): Observable<LoginViewState> =
            viewState.serialize()

    fun bindView(loginView: LoginView) {
        disposable.add(loginView.startLogin().subscribe {
            authenticateUser(it)
        })
        disposable.add(loginView.emailFilled().subscribe {
            val emailValid = formValidator.isEmailValid(it)
            viewState.onNext(LoginViewState(emailErrorMessage = emailValid))
            formValidator.emailValidSubject.onNext(emailValid.isEmpty())
        })
        disposable.add(loginView.passwordFilled().subscribe {
            val passwordValid = formValidator.isPasswordValid(it)
            viewState.onNext(LoginViewState(passwordErrorMessage = passwordValid))
            formValidator.passwordValidSubject.onNext(passwordValid.isEmpty())
        })

        disposable.add(formValidator.isValid().subscribe {
            viewState.onNext(LoginViewState(enableSubmit = it))
        })
    }

    private fun authenticateUser(loginInfo: LoginInfo) {
        val loginCredential = User(loginInfo.email, loginInfo.password)
        val loginResponse = repository.login(loginCredential)

        if (loginResponse) {
            viewState.onNext(LoginViewState(isUserLogged = true))
        } else {
            viewState.onNext(LoginViewState(isError = true))
        }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(LoginRepository(), LoginValidator(context)) as T
        }
    }
}
