package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.*
import android.content.Context
import com.rviannaoliveira.vformmvvm.core.Status
import com.rviannaoliveira.vformmvvm.model.LoginInfo
import com.rviannaoliveira.vformmvvm.model.LoginViewState
import com.rviannaoliveira.vformmvvm.model.User
class LoginViewModelWithLiveData(private val repository: LoginRepository,
                                 private val loginValidator: LoginValidator) : ViewModel() {


    private var loginViewState = LoginViewState()
    val emailValidator = loginValidator.emailError
    val passwordValidator = loginValidator.passwordError
    val enableButton = loginValidator.enableButton

    val submitObserver = MediatorLiveData<LoginViewState>()
    private val userObserver = MutableLiveData<User>()
    private val resultLoginUser = Transformations.switchMap(userObserver) { user ->
        repository.loginWithLiveData(user)
    }

    init {
        submitObserver.addSource(resultLoginUser) {
            submitObserver.value = loginViewState.copy(
                    isUserLogged = Status.SUCCESS == it!!.status,
                    showProgress = Status.LOADING == it.status,
                    isError = Status.ERROR == it.status)
        }
    }

    fun authenticateUser(loginInfo: LoginInfo) {
        userObserver.value = User(loginInfo.email, loginInfo.password)
    }

    fun validEmail(text: String) {
        loginViewState = loginViewState.copy(email = text)
        loginValidator.isEmailValid(loginViewState)
    }

    fun validPassword(text: String) {
        loginViewState = loginViewState.copy(password = text)
        loginValidator.isPasswordValid(loginViewState)
    }

    class Factory(private val requireContext: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModelWithLiveData(LoginRepository(), LoginValidator(requireContext)) as T
        }
    }
}

