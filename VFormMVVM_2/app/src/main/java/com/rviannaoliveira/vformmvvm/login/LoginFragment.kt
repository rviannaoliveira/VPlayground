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
import com.rviannaoliveira.vformmvvm.R.id.*
import com.rviannaoliveira.vformmvvm.model.LoginInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit


interface ILoginView {
    fun startLogin(): Observable<LoginInfo>
    fun emailFilled(): Observable<String>
    fun passwordFilled(): Observable<String>
}

class LoginFragment : Fragment(), ILoginView {
    private lateinit var viewModel: LoginViewModel
    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = LoginViewModel.Factory()
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        viewModel.bindView(this, LoginValidator(requireContext()))
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    override fun onPause() {
        disposable.clear()
        super.onPause()
    }

    override fun onDestroyView() {
        viewModel.unbindView()
        super.onDestroyView()
    }

    private fun init() {
        disposable.add(viewModel.expectedResult()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loginViewState ->
                    hideKeyboard()
                    submit.isEnabled = loginViewState.enableSubmit
                    inputLayoutEmail.error = if(loginViewState.emailErrorMessage.isNotEmpty()) loginViewState.emailErrorMessage else null
                    inputLayoutPassword.error = if(loginViewState.passwordErrorMessage.isNotEmpty()) loginViewState.passwordErrorMessage else null
                    progressBar.visibility =  if(loginViewState.showProgress) View.VISIBLE else View.GONE

                    when {
                        loginViewState.isUserLogged -> {
                            hideKeyboard()
                            Snackbar.make(view!!, R.string.success, Snackbar.LENGTH_LONG).show()
                        }
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

    companion object {
        fun newInstance() = LoginFragment()
        private const val TIME_DELAY = 800.toLong()
    }

}
