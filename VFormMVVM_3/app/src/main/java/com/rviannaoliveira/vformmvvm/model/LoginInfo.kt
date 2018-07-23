package com.rviannaoliveira.vformmvvm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginInfo(
        val email: String,
        val password: String) : Parcelable

data class LoginViewState(
        val email: String = "",
        val password: String = "",
        val emailErrorMessage: String = "",
        val passwordErrorMessage: String = "",
        val isError: Boolean = false,
        val showProgress : Boolean =  false,
        val isUserLogged : Boolean = false,
        val enableSubmit : Boolean = false
)


