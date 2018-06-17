package com.rviannaoliveira.vbasicmvvmsample.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.MapKey
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import javax.inject.Inject
import kotlin.reflect.KClass

@Suppress("DEPRECATED_JAVA_ANNOTATION")
@MustBeDocumented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

class ViewModelFactory<T : ViewModel> @Inject constructor(private val viewModel: Lazy<T>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel.value as T
}

//@Module
//class ViewModelModule {
//    @ViewModelKey(HomeViewModel::class)
//    fun bindRepoViewModel(homeViewModel: HomeViewModel): ViewModel
//}
