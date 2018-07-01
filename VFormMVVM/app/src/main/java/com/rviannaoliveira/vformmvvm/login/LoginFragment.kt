package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.rviannaoliveira.vformmvvm.R
import com.rviannaoliveira.vformmvvm.databinding.FragmentLoginBinding

class LoginFragment :Fragment() {
    private lateinit var dataBinding: FragmentLoginBinding
    private lateinit var viewModel : LoginViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        dataBinding.setLifecycleOwner(this)

        val factory = LoginViewModel.Factory()
        val loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        dataBinding.viewModel = loginViewModel
        setupUI(loginViewModel)
        return dataBinding.root
    }

    private fun setupUI(loginViewModel: LoginViewModel) {
        loginViewModel.message.observe(this, Observer {
            it?.getContent()?.let {
                hideKeyboard()
                Snackbar.make(dataBinding.root, it, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun hideKeyboard() {
        activity?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.currentFocus.windowToken, 0)
        }

    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}