package com.rviannaoliveira.vphysicsbasedanimation

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_chain.*


class DragBackSpringActivity : AppCompatActivity() {
    private val springTranslationXAnimation by lazy { SpringAnimation(drag,DynamicAnimation.TRANSLATION_X) }
    private val springTranslationYAnimation by lazy { SpringAnimation(drag,DynamicAnimation.TRANSLATION_Y) }

    private var diffTouchPointX: Float = 0f
    private var diffTouchPointY: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_back)
        setupAnimation()
    }

    private fun setupAnimation() {
        improveEffects()

        drag.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    springTranslationXAnimation.start()
                    springTranslationYAnimation.start()
                }
                MotionEvent.ACTION_DOWN -> {
                    diffTouchPointX = event.rawX - view.x
                    diffTouchPointY = event.rawY - view.y
                    springTranslationXAnimation.cancel()
                    springTranslationYAnimation.cancel()
                }
                MotionEvent.ACTION_MOVE -> {
                    val newTopLeftX = event.rawX - diffTouchPointX
                    val newTopLeftY = event.rawY - diffTouchPointY
                    view.x = newTopLeftX
                    view.y = newTopLeftY

                }

            }

            return@setOnTouchListener true
        }
    }

    private fun improveEffects() {
        val springForce = SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        }

        springTranslationXAnimation.spring = springForce
        springTranslationYAnimation.spring = springForce
    }
}