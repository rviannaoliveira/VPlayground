package com.rviannaoliveira.vformmvvm.model

data class LoginInfo(
        val email: String,
        val password: String
)

data class LoginViewState(
        val emailErrorMessage: String = "",
        val passwordErrorMessage: String = "",
        val isError: Boolean = false,
        val isUserLogged : Boolean = false,
        val enableSubmit : Boolean = false
)


