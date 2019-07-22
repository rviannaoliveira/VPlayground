package com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.MainRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

sealed class MainState(val isLoading: Boolean) {
    object Success : MainState(isLoading = false)
    object Error : MainState(isLoading = false)
    object LoadingState : MainState(isLoading = true)
    object ShowSomething : MainState(isLoading = false)
}

class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val resources: Resources,
    @Named("IOScheduler") private val ioScheduler: Scheduler,
    @Named("MainScheduler") private val mainScheduler: Scheduler
) : ViewModel() {

    private val disposable = CompositeDisposable()
    val state = MutableLiveData<MainState>()
    val uiModel = MutableLiveData<MainUiModel>()

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        disposable.add(repository.getCharacter()
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .doOnSubscribe { state.value = MainState.LoadingState }
            .subscribe({ character ->
                state.value = MainState.Success
                uiModel.value = MainUiModel(
                    resources = resources,
                    character = character
                )
            }, {
                state.value = MainState.Error
            })
        )
    }

    fun showSomething(){
        state.value = MainState.ShowSomething
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}