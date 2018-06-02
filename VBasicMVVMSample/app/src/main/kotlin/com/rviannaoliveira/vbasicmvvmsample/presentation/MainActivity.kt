package com.rviannaoliveira.vbasicmvvmsample.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rviannaoliveira.core.presentation.replace
import com.rviannaoliveira.vbasicmvvmsample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.replace(container.id,ItemsFragment.newInstance())
    }
}
