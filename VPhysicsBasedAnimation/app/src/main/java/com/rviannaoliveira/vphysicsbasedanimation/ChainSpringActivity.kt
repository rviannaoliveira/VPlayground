package com.rviannaoliveira.vphysicsbasedanimation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import kotlinx.android.synthetic.main.activity_chain.*

class ChainSpringActivity : AppCompatActivity() {

    private val firstXAnim by lazy { SpringAnimation(first, DynamicAnimation.X) }
    private val firstYAnim by lazy { SpringAnimation(first, DynamicAnimation.Y) }
    private val secondXAnim by lazy { SpringAnimation(second, DynamicAnimation.X) }
    private val secondYAnim by lazy { SpringAnimation(second, DynamicAnimation.Y) }

    private var dX = 0f
    private var dY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chain)
        setupAnimation()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupAnimation() {
        val firstLayoutParams = first.layoutParams as ViewGroup.MarginLayoutParams
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

        drag.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    val newY = event.rawY + dY

                    view.animate().x(newX).y(newY).setDuration(0).start()
                    firstXAnim.animateToFinalPosition(
                        newX + ((drag.width -
                                first.width) / 2)
                    )
                    firstYAnim.animateToFinalPosition(
                        newY + drag.height +
                                firstLayoutParams.topMargin
                    )
                }
            }

            return@setOnTouchListener true
        }
    }
}