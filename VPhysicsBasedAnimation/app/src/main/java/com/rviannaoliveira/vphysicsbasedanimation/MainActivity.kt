package com.rviannaoliveira.vphysicsbasedanimation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        action_simple.setOnClickListener {
            startActivity(Intent(this, SimpleSpringActivity::class.java))
        }


        action_drag_back.setOnClickListener {
            startActivity(Intent(this, DragBackSpringActivity::class.java))
        }

        action_fling.setOnClickListener {
            startActivity(Intent(this, FlingActivity::class.java))
        }
        action_rotate.setOnClickListener {
            startActivity(Intent(this, RotateSpringActivity::class.java))
        }

        action_chain.setOnClickListener {
            startActivity(Intent(this, ChainSpringActivity::class.java))
        }
    }
}