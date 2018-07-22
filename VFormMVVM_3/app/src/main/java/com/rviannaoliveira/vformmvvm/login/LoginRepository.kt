package com.rviannaoliveira.vformmvvm.login

import com.rviannaoliveira.vformmvvm.model.User
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class LoginRepository {
    fun login(user: User): Observable<Boolean> {
        return Observable
                .timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map {
                    print(">>>>$user")
                    true
                }
    }
}