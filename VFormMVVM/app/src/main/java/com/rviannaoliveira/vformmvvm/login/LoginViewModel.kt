package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rviannaoliveira.vformmvvm.R
import com.rviannaoliveira.vformmvvm.core.SingleEvent
import com.rviannaoliveira.vformmvvm.model.User

class LoginViewModel(private val repository: LoginRepository,
                     val formValidator: LoginValidator): ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val userLogged = MutableLiveData<SingleEvent<Boolean>>()
    val message = MutableLiveData<SingleEvent<Int>>()

    fun onUserSubmit() {
        if (formValidator.isValid(email.value, password.value)) {
            authenticateUser()
        } else {
            message.value = SingleEvent(R.string.invalid_form)
        }
    }

    private fun authenticateUser()  {
        val loginCredential = User(email.value!!, password.value!!)
        val loginResponse = repository.login(loginCredential)

        if (loginResponse) {
            userLogged.value = SingleEvent(true)
        } else {
            message.value = SingleEvent(R.string.error)
        }

    }

    class Factory: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(LoginRepository(), LoginValidator()) as T
        }
    }
}