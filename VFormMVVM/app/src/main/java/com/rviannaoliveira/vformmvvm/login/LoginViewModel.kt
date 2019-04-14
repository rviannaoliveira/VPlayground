package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.rviannaoliveira.vformmvvm.R
import com.rviannaoliveira.vformmvvm.core.SingleEvent
import com.rviannaoliveira.vformmvvm.model.User
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class LoginViewModel(private val repository: LoginRepository,
                     val formValidator: LoginValidator) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val userLogged = MutableLiveData<SingleEvent<Boolean>>()
    val message = MutableLiveData<SingleEvent<Int>>()
    val publishSubject = PublishSubject.create<String>()
    val test = MutableLiveData<String>()

    init {
        publishSubject
                .filter { it.isNotEmpty() }
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribe {
                    Log.d(">>>>>>>>", ">>>>>>>>>>1111$it")
                    test.postValue("")
                }
    }


    fun onUsernameTextChanged(text: CharSequence) {
        Log.d(">>>>>>>>", ">>>>>>>>>>3333$text")
        publishSubject.onNext(text.toString())
    }

    fun onUserSubmit() {
        if (formValidator.isValid(email.value, password.value)) {
            authenticateUser()
        } else {
            message.value = SingleEvent(R.string.invalid_form)
        }
    }

    private fun authenticateUser() {
        val loginCredential = User(email.value!!, password.value!!)
        val loginResponse = repository.login(loginCredential)

        if (loginResponse) {
            userLogged.value = SingleEvent(true)
        } else {
            message.value = SingleEvent(R.string.error)
        }

    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(LoginRepository(), LoginValidator()) as T
        }
    }
}