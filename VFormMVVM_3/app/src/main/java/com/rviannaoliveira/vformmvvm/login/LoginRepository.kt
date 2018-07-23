package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.LiveData
import com.rviannaoliveira.vformmvvm.core.Resource
import com.rviannaoliveira.vformmvvm.model.User
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class LoginRepository {
    fun loginWithLiveData(user: User): LiveData<Resource<Boolean>> {
        return ApiLiveData(user)
    }
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


class ApiLiveData(private val user: User) : LiveData<Resource<Boolean>>() {
    var disposable: Disposable? = null

    override fun onActive() {
        disposable = Observable
                .timer(5, TimeUnit.SECONDS)
                .doOnSubscribe {
                    postValue(Resource.loading(true))
                }
                .map {
                    print(">>>>$user")
                    true
                }
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    postValue(Resource.success(response))
                }, {
                    postValue(Resource.error("error", false))
                })

    }

    override fun onInactive() {
        if (disposable != null)
            disposable!!.dispose()
    }
}