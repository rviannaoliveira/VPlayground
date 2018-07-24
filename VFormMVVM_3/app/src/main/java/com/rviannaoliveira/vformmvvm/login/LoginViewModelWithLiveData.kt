package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.*
import android.content.Context
import com.rviannaoliveira.vformmvvm.core.Status
import com.rviannaoliveira.vformmvvm.model.LoginViewState
import com.rviannaoliveira.vformmvvm.model.User

class LoginViewModelWithLiveData(private val repository: LoginRepository,
                                 private val loginValidator: LoginValidator) : ViewModel() {

    private var loginViewState = LoginViewState()
    val emailValidator = loginValidator.emailError
    val passwordValidator = loginValidator.passwordError
    val enableButton = loginValidator.enableButton

    val successObserver = MediatorLiveData<Unit>()
    val loadingObserver = MediatorLiveData<Boolean>()
    val errorObserver = MediatorLiveData<String>()

    private val submitObserver = MutableLiveData<User>()
    private val resultLoginUser = Transformations.switchMap(submitObserver) { user ->
        repository.loginWithLiveData(user)
    }

    init {
        successObserver.addSource(resultLoginUser) {
            if (Status.SUCCESS == it!!.status) {
                successObserver.value = Unit
            }
        }

        loadingObserver.addSource(resultLoginUser) {
            if (Status.LOADING == it!!.status) {
                loadingObserver.value = it.data
            }
        }
        errorObserver.addSource(resultLoginUser) {
            if (Status.ERROR == it!!.status) {
                errorObserver.value = it.message
            }
        }
    }

    fun authenticateUser() {
        submitObserver.value = User(loginViewState.email, loginViewState.password)
    }

    fun validEmail(text: String) {
        loginViewState = loginViewState.copy(email = text)
        loginValidator.isEmailValid(loginViewState)
    }

    fun validPassword(text: String) {
        loginViewState = loginViewState.copy(password = text)
        loginValidator.isPasswordValid(loginViewState)
    }

    override fun onCleared() {
        loginValidator.disposable()
        super.onCleared()
    }

    class Factory(private val requireContext: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModelWithLiveData(LoginRepository(), LoginValidator(requireContext)) as T
        }
    }
}

