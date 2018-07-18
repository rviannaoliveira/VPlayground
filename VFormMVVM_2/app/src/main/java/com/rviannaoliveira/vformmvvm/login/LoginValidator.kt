package com.rviannaoliveira.vformmvvm.login

import android.content.Context
import com.rviannaoliveira.vformmvvm.R
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class LoginValidator    (private val context: Context) {
    val emailValidSubject: PublishSubject<Boolean> = PublishSubject.create()
    val passwordValidSubject: PublishSubject<Boolean> = PublishSubject.create()

    fun isPasswordValid(passwordString: String?): String  {
        var text = ""

        if (passwordString.isNullOrBlank()) {
            text = context.getString(R.string.error_required)
        }
        if (passwordString!!.length < 8) {
            text = context.getString(R.string.invalid_password)
        }
        return text
    }

    fun isEmailValid(emailString: String?): String {
        var text = ""

        if (emailString.isNullOrBlank()) {
            text = context.getString(R.string.error_required)
        }
        if (!emailString!!.contains("@")) {
            text = context.getString(R.string.invalid_email)
        }
        return text
    }

    fun isValid() : Observable<Boolean> =  Observable.combineLatest(
            emailValidSubject,
            passwordValidSubject,
            BiFunction { t1: Boolean, t2: Boolean -> t1 && t2 })
}