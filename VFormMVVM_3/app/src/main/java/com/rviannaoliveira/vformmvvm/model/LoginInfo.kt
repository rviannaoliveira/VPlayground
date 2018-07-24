package com.rviannaoliveira.vformmvvm.model

data class LoginViewState(
        val email: String = "",
        val password: String = "",
        val emailErrorMessage: String = "",
        val passwordErrorMessage: String = "",
        val enableSubmit : Boolean = false
)


