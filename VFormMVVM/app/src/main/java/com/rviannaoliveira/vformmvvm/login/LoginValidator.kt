package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.MutableLiveData
import com.rviannaoliveira.vformmvvm.R


class LoginValidator {
    val emailError = MutableLiveData<Int>()
    val passwordError = MutableLiveData<Int>()

    fun isValid(email: String?, password: String?): Boolean {
        val emailValid = isEmailValid(email, emailError)
        val passwordValid = isPasswordValid(password, passwordError)

        return emailValid && passwordValid
    }

    private fun isPasswordValid(passwordString: String?, error: MutableLiveData<Int>): Boolean {
        if (passwordString.isNullOrBlank()) {
            error.value = R.string.error_required
            return false
        }
        if (passwordString!!.length < 8) {
            error.value = R.string.invalid_password
            return false
        }
        error.value = null
        return true
    }

    private fun isEmailValid(emailString: String?, error: MutableLiveData<Int>): Boolean {
        if (emailString.isNullOrBlank()) {
            error.value = R.string.error_required
            return false
        }
        if (!emailString!!.contains("@")){
            error.value = R.string.invalid_email
            return false
        }
        error.value = null
        return true
    }
}