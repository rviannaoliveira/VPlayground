package com.rviannaoliveira.vformmvvm.login

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
import com.rviannaoliveira.vformmvvm.model.LoginInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit


interface LoginView {
    fun startLogin(): Observable<LoginInfo>
    fun emailFilled(): Observable<String>
    fun passwordFilled(): Observable<String>
}

class LoginFragment : Fragment(), LoginView {
    private lateinit var viewModel: LoginViewModel
    private val disposable = CompositeDisposable()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = LoginViewModel.Factory(context!!)
        val loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

        loginViewModel.bindView(this)
        init(loginViewModel)
    }


    private fun init(loginViewModel: LoginViewModel) {
        disposable.add(loginViewModel.expectedResult().subscribe { loginViewState ->
            inputLayoutEmail.error = null
            inputLayoutPassword.error = null
            submit.isEnabled = loginViewState.enableSubmit
            hideKeyboard()

            when {
                loginViewState.isUserLogged -> {
                    hideKeyboard()
                    Snackbar.make(view!!, R.string.success, Snackbar.LENGTH_LONG).show()
                }
                loginViewState.emailErrorMessage.isNotEmpty() -> inputLayoutEmail.error = loginViewState.emailErrorMessage
                loginViewState.passwordErrorMessage.isNotEmpty() -> inputLayoutPassword.error = loginViewState.passwordErrorMessage
                loginViewState.isError -> Snackbar.make(view!!, getString(R.string.error), Snackbar.LENGTH_LONG).show()
            }
        })

    }

    private fun hideKeyboard() {
        activity?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.currentFocus.windowToken, 0)
        }

    }

    override fun startLogin(): Observable<LoginInfo> = submit.clicks()
            .map {
                LoginInfo(
                        email = inputEmail.text.toString(),
                        password = inputPassword.text.toString())
            }


    override fun emailFilled(): Observable<String> = inputEmail.textChanges()
            .throttleWithTimeout(TIME_DELAY, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { text -> text.toString() }
            .filter { text -> text.isNotEmpty() }

    override fun passwordFilled(): Observable<String> = inputPassword.textChanges()
            .throttleWithTimeout(TIME_DELAY, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { text -> text.toString() }
            .filter { text -> text.isNotEmpty() }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = LoginFragment()
        private const val TIME_DELAY = 800.toLong()
    }

}
