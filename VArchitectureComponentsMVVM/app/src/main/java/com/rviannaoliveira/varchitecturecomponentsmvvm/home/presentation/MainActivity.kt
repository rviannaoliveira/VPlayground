package com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.rviannaoliveira.varchitecturecomponentsmvvm.R
import com.rviannaoliveira.varchitecturecomponentsmvvm.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(){
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var vm: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)

        binding = setContentView(this, R.layout.activity_main)

        vm = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.vm = vm
        setupObserver()
    }

    private fun setupObserver() {
        vm.state.observe(this, Observer {state ->
            when(state){
                is MainState.Error -> Snackbar.make(binding.root,getString(R.string.error),Snackbar.LENGTH_LONG).show()
                is MainState.Success -> Snackbar.make(binding.root,getString(R.string.ok),Snackbar.LENGTH_LONG).show()
                is MainState.ShowSomething ->  Toast.makeText(this,getString(R.string.testing),Toast.LENGTH_SHORT).show()
            }
        })

    }
}
