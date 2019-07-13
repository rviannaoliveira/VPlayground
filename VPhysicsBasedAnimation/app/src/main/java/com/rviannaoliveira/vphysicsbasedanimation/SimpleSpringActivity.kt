package com.rviannaoliveira.vphysicsbasedanimation

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_chain.first
import kotlinx.android.synthetic.main.activity_chain.second
import kotlinx.android.synthetic.main.activity_simple.*

class SimpleSpringActivity : AppCompatActivity() {

    private val firstXAnim by lazy { SpringAnimation(first, DynamicAnimation.X) }
    private val firstYAnim by lazy { SpringAnimation(first, DynamicAnimation.Y) }
    private val secondXAnim by lazy { SpringAnimation(second, DynamicAnimation.X) }
    private val secondYAnim by lazy { SpringAnimation(second, DynamicAnimation.Y) }
    private var upDownValue: Float = 0f
    private var leftRightValue: Float = 0f
    private var originalAbsX : Float = 0f
    private var originalAbsY : Float = 0f
    private val MOVE = 50f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        setupAnimation()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val originalPositionFirst = IntArray(2)

        first.getLocationOnScreen(originalPositionFirst)
        originalAbsX =  originalPositionFirst[0].toFloat()
        originalAbsY =  originalPositionFirst[1].toFloat()
        firstXAnim.animateToFinalPosition(originalAbsX)
        firstYAnim.animateToFinalPosition(originalAbsY)
    }

    private fun setupAnimation() {
        val secondLayoutParams = second.layoutParams as ViewGroup.MarginLayoutParams

        firstXAnim.addUpdateListener { _, value, _ ->
            secondXAnim.animateToFinalPosition(
                value + ((first.width -
                        second.width) / 2)
            )
        }
        firstYAnim.addUpdateListener { _, value, _ ->
            secondYAnim.animateToFinalPosition(
                value + first.height +
                        secondLayoutParams.topMargin
            )
        }

        down.setOnClickListener {
            upDownValue += MOVE
            firstYAnim.animateToFinalPosition(
                upDownValue + originalAbsY
            )
        }

        up.setOnClickListener {
            upDownValue += - MOVE
            firstYAnim.animateToFinalPosition(
                upDownValue + originalAbsY
            )
        }

        left.setOnClickListener {
            leftRightValue += - MOVE
            firstXAnim.animateToFinalPosition(
                leftRightValue + originalAbsX
            )
        }

        right.setOnClickListener {
            leftRightValue += MOVE

            firstXAnim.animateToFinalPosition(
                leftRightValue  + originalAbsX
            )
        }

        clear.setOnClickListener {
            upDownValue = 0f
            leftRightValue = 0f

            firstXAnim.animateToFinalPosition(originalAbsX)

            firstYAnim.animateToFinalPosition(originalAbsY)
        }

    }
}