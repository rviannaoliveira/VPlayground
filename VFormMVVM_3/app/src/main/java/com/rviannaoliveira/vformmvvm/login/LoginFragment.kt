package com.rviannaoliveira.vformmvvm.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.rviannaoliveira.vformmvvm.R
import com.rviannaoliveira.vformmvvm.model.LoginViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModelWithLiveData
    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = LoginViewModelWithLiveData.Factory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModelWithLiveData::class.java)
        setupUI()
    }

    private fun setupUI() {
        viewModel.emailValidator.observe(this, Observer<LoginViewState> { state ->
            inputLayoutEmail.error = if (state?.emailErrorMessage!!.isNotEmpty()) state.emailErrorMessage else null
        })
        viewModel.passwordValidator.observe(this, Observer<LoginViewState> { state ->
            inputLayoutPassword.error = if (state?.passwordErrorMessage!!.isNotEmpty()) state.passwordErrorMessage else null
        })
        viewModel.enableButton.observe(this, Observer<Boolean> {
            submit.isEnabled = it!!
        })
        viewModel.successObserver.observe(this, Observer<Unit> {
            hideKeyboard()
            Snackbar.make(view!!, R.string.success, Snackbar.LENGTH_LONG).show()
        })
        viewModel.loadingObserver.observe(this, Observer<Boolean> {
            progressBar.visibility = if (it!!) View.VISIBLE else View.GONE
        })
        viewModel.errorObserver.observe(this, Observer<String> { it ->
            Snackbar.make(view!!, it!!, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun init() {
        disposable.add(emailFilled())
        disposable.add(passwordFilled())
        disposable.add(startLogin())
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    override fun onPause() {
        disposable.clear()
        super.onPause()
    }


    private fun hideKeyboard() {
        activity?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.currentFocus.windowToken, 0)
        }

    }

    private fun startLogin() = submit.clicks()
            .subscribe {
                viewModel.authenticateUser()
            }


    private fun emailFilled() = inputEmail.textChanges()
            .throttleWithTimeout(TIME_DELAY, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { text -> text.toString() }
            .filter { text -> text.isNotEmpty() }
            .subscribe {
                viewModel.validEmail(it)
            }

    private fun passwordFilled() = inputPassword.textChanges()
            .throttleWithTimeout(TIME_DELAY, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { text -> text.toString() }
            .filter { text -> text.isNotEmpty() }
            .subscribe {
                viewModel.validPassword(it)
            }

    companion object {
        fun newInstance() = LoginFragment()
        private const val TIME_DELAY = 500.toLong()
    }

}
