package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.rviannaoliveira.vformmvvm.R
import com.rviannaoliveira.vformmvvm.model.LoginViewState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class LoginValidator(private val context: Context) {
    val emailError = MutableLiveData<LoginViewState>()
    val passwordError = MutableLiveData<LoginViewState>()
    val enableButton = MutableLiveData<Boolean>()
    private val emailValidSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val passwordValidSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val disposable = CompositeDisposable()

    init {
        disposable.add(isValid().subscribe {
            enableButton.value = it
        })
    }


    fun isPasswordValid(loginViewState: LoginViewState) {
        var text = ""

        if (loginViewState.password.length < 8) {
            text = context.getString(R.string.invalid_password)
        }

        val loginViewStateCopy = loginViewState.copy(passwordErrorMessage = text)
        passwordError.value = loginViewStateCopy
        passwordValidSubject.onNext(loginViewStateCopy.passwordErrorMessage.isEmpty())
    }

    fun isEmailValid(loginViewState: LoginViewState) {
        var text = ""

        if ("@" !in loginViewState.email) {
            text = context.getString(R.string.invalid_email)
        }

        val loginViewStateCopy = loginViewState.copy(emailErrorMessage = text)
        emailError.value = loginViewStateCopy
        emailValidSubject.onNext(loginViewStateCopy.emailErrorMessage.isEmpty())
    }

    fun disposable() = disposable.clear()

    private fun isValid(): Observable<Boolean> = Observable.combineLatest(
            emailValidSubject,
            passwordValidSubject,
            BiFunction { emailState: Boolean, passwordState: Boolean ->
                emailState && passwordState
            })

}