package com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.rviannaoliveira.varchitecturecomponentsmvvm.R
import com.rviannaoliveira.varchitecturecomponentsmvvm.databinding.ActivityMainBinding
import com.rviannaoliveira.shared.livedata.SafeObserver
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.di.MainModule
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.scope.lifecycleScope
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import javax.inject.Inject

class MainActivity : AppCompatActivity(){
    private val viewModel by lazy { get<MainViewModel>() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        loadKoinModules(MainModule.instance)
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        binding = setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.vm = viewModel
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state){
                is MainState.Error -> Snackbar.make(binding.root,getString(R.string.error),Snackbar.LENGTH_LONG).show()
                is MainState.Success -> Snackbar.make(binding.root,getString(R.string.ok),Snackbar.LENGTH_LONG).show()
                is MainState.ShowSomething ->  Toast.makeText(this,getString(R.string.testing),Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(MainModule.instance)
    }
}
