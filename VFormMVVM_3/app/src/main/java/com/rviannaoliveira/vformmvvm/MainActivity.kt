package com.rviannaoliveira.vformmvvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rviannaoliveira.vformmvvm.core.replace
import com.rviannaoliveira.vformmvvm.login.LoginFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.replace(container.id,LoginFragment.newInstance())
    }
}
