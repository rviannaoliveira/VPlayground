package com.rviannaoliveira.vphysicsbasedanimation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import kotlinx.android.synthetic.main.activity_rotate.*


class RotateSpringActivity : AppCompatActivity() {
    private val firstRotationAnimation: SpringAnimation by lazy { SpringAnimation(first, DynamicAnimation.ROTATION) }
    private val secondRotationAnimation by lazy { SpringAnimation(second, DynamicAnimation.ROTATION) }
    private val springForce by lazy {
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate)

        firstRotationAnimation.addUpdateListener { _, _, _ ->
            secondRotationAnimation.run {
                spring = springForce
                setStartValue(600f)
                start()
            }
        }

        action.setOnClickListener {
            firstRotationAnimation.run {
                spring = springForce
                setStartValue(-30f)
                start()
            }

        }
    }
}