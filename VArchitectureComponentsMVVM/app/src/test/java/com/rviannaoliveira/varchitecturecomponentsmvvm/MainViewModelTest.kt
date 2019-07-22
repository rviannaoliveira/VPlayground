package com.rviannaoliveira.varchitecturecomponentsmvvm

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.MainRepository
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.model.Character
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation.MainState
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation.MainViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel : MainViewModel
    private val iosScheduler = Schedulers.trampoline()
    private val mainScheduler = Schedulers.trampoline()
    @Mock
    private lateinit var resource : Resources
    @Mock
    private lateinit var repository : MainRepository
    @Mock
    private lateinit var character : Character
    @Mock
    private lateinit var stateObserver: Observer<MainState>

    @Test
    fun `should load the character when the user enter in the screen`(){
        given(repository.getCharacter()).willReturn(Single.just(character))
        initViewModel()

        verify(stateObserver).onChanged(MainState.LoadingState)
        verify(stateObserver).onChanged(MainState.Success)
    }

    @Test
    fun `should show msg when some wrong happens`(){
        val exception = Exception()
        given(repository.getCharacter()).willReturn(Single.error(exception))
        initViewModel()

        verify(stateObserver).onChanged(MainState.Error)
    }

    @Test
    fun `should do something when the user click in the image`(){
        given(repository.getCharacter()).willReturn(Single.just(character))
        initViewModel()

        viewModel.showSomething()

        verify(stateObserver).onChanged(MainState.ShowSomething)
    }

    private fun initViewModel(){
        viewModel = MainViewModel(repository,resource,iosScheduler,mainScheduler)

        viewModel.state.observeForever(stateObserver)
    }

}