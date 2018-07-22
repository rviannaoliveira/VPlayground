package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.LiveData
import com.rviannaoliveira.vformmvvm.model.User
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class ApiLiveData(private val user: User) : LiveData<Boolean>() {
    var disposable: Disposable? = null

    override fun onActive() {
        disposable =  Observable
                .timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map {
                    print(">>>>$user")
                    true
                }
                .subscribeOn(Schedulers.io())
                .subscribe { response ->
                    postValue(response)
                }

    }

    override fun onInactive() {
        if (disposable != null)
            disposable!!.dispose()
    }
}