package com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rviannaoliveira.shared.base.BaseViewModel
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.domain.CharacterHero
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.domain.MainUsecase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

sealed class MainState(val isLoading: Boolean) {
    object Success : MainState(isLoading = false)
    object Error : MainState(isLoading = false)
    object LoadingState : MainState(isLoading = true)
    object ShowSomething : MainState(isLoading = false)
}

class MainViewModel(
    private val interactor: MainUsecase,
    private val resources: Resources,
    private val dispatcherIO: CoroutineDispatcher
) : BaseViewModel() {
    val state = MutableLiveData<MainState>()
    val uiModel = MutableLiveData<MainUiModel>()

    override fun onResume() {
        super.onResume()
        viewModelScope.launch(dispatcherIO) {
            interactor.getCharacter()
                .onFailure { error ->
                    println(">>>>ERRO CARALHO ${error.message}")
                    state.postValue(MainState.Error)
                    error.printStackTrace()
                }.onSuccess {
                    state.postValue(MainState.Success)
                    uiModel.postValue(
                        MainUiModel(
                            resources = resources,
                            character = it
                        )
                    )
                }
        }
    }

    fun showSomething() {
        state.value = MainState.ShowSomething
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}