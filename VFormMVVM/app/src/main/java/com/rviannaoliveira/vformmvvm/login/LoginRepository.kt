package com.rviannaoliveira.vformmvvm.login

import com.rviannaoliveira.vformmvvm.model.User


class LoginRepository {
    fun login(user: User): Boolean {
        println("login: $user")
        return true
    }
}