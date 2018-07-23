package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.rviannaoliveira.vformmvvm.R
import com.rviannaoliveira.vformmvvm.model.LoginViewState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class LoginValidator(private val context: Context) {
    val emailError = MutableLiveData<LoginViewState>()
    val passwordError = MutableLiveData<LoginViewState>()
    val enableButton = MutableLiveData<LoginViewState>()
    private val emailValidSubject: PublishSubject<LoginViewState> = PublishSubject.create()
    private val passwordValidSubject: PublishSubject<LoginViewState> = PublishSubject.create()

    init {
        isValid().subscribe {
            enableButton.value = it
        }
    }


    fun isPasswordValid(loginViewState: LoginViewState) {
        var text = ""

        if (loginViewState.password.isBlank()) {
            text = context.getString(R.string.error_required)
        }
        if (loginViewState.password.length < 8) {
            text = context.getString(R.string.invalid_password)
        }

        val loginViewStateCopy = loginViewState.copy(passwordErrorMessage = text)
        passwordError.value = loginViewStateCopy
        passwordValidSubject.onNext(loginViewStateCopy)
    }

    fun isEmailValid(loginViewState: LoginViewState) {
        var text = ""

        if (loginViewState.email.isBlank()) {
            text = context.getString(R.string.error_required)
        }
        if ("@" !in loginViewState.email) {
            text = context.getString(R.string.invalid_email)
        }

        val loginViewStateCopy = loginViewState.copy(emailErrorMessage = text)
        emailError.value = loginViewStateCopy
        emailValidSubject.onNext(loginViewStateCopy)
    }

    private fun isValid(): Observable<LoginViewState> = Observable.combineLatest(
            emailValidSubject,
            passwordValidSubject,
            BiFunction { emailState: LoginViewState, passwordState: LoginViewState ->
                LoginViewState(enableSubmit = emailState.emailErrorMessage.isEmpty() && passwordState.passwordErrorMessage.isEmpty())
            })

}