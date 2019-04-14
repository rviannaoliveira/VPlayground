package com.rviannaoliveira.vconstraintlayouanimation

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class MainActivity : AppCompatActivity() {
    private lateinit var text : TextView
    private lateinit var constraint : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        constraint = findViewById(R.id.constraint)
        text = findViewById(R.id.text)
        text.setOnClickListener {
            showComponents()
        }
    }

    private fun showComponents() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_main2)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 5000

        TransitionManager.beginDelayedTransition(constraint,transition)
        constraintSet.applyTo(constraint) //here constraint is the name of view to which we are applying the constraintSet
    }
}
