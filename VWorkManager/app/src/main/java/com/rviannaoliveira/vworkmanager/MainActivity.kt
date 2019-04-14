package com.rviannaoliveira.vworkmanager

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG_DOWNLOAD_WORKER = "TAG_DOWNLOAD_WORKER"
    }

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        viewModel.livedataWorker.observe(this, Observer { info ->
                if(info!!.isNotEmpty()){
                }
            })


        findViewById<View>(R.id.click).setOnClickListener {
            viewModel.runWorker()
        }
    }
}
