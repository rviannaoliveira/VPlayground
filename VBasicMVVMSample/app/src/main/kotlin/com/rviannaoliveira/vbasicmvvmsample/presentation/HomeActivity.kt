package com.rviannaoliveira.vbasicmvvmsample.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rviannaoliveira.core.presentation.replace
import com.rviannaoliveira.vbasicmvvmsample.R
import com.rviannaoliveira.vbasicmvvmsample.di.AppComponent
import com.rviannaoliveira.vbasicmvvmsample.di.AppInjector
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.replace(container.id,HomeFragment.newInstance())
    }
}
