package com.rviannaoliveira.vbasicmvvmsample.presentation

import android.arch.lifecycle.ViewModel
import com.rviannaoliveira.repository.marvel.IMarvelApiRepository
import com.rviannaoliveira.repository.marvel.presentation.model.MarvelCharacter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: IMarvelApiRepository) : ViewModel(){
    private val disposable : CompositeDisposable = CompositeDisposable()

    private val stateObservable : BehaviorSubject<HomeViewState> by lazy { BehaviorSubject.create<HomeViewState>() }

    fun loadCharacters() {
        disposable.add(repository.getMarvelCharacters(0)
                .doOnSubscribe { stateObservable.onNext(HomeViewState(isLoading = true)) }
                .subscribe { list ->
            stateObservable.onNext(HomeViewState(list))
        })
    }

    fun getViewState() : Observable<HomeViewState> =
        stateObservable.serialize().hide()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}


data class HomeViewState(val list : List<MarvelCharacter> =  listOf(), val isLoading : Boolean = false)