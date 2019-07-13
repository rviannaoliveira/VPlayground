package com.rviannaoliveira.vphysicsbasedanimation

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import com.rviannaoliveira.vphysicsbasedanimationandroid.afterMeasured
import kotlinx.android.synthetic.main.activity_chain.drag
import kotlinx.android.synthetic.main.activity_fling.*
import kotlinx.android.synthetic.main.activity_fling.view.*

class FlingActivity : AppCompatActivity() {
    private val flingAnimationX: FlingAnimation by lazy { FlingAnimation(drag, DynamicAnimation.X) }
    private val flingAnimationY: FlingAnimation by lazy { FlingAnimation(drag, DynamicAnimation.Y) }
    private var maxTranslationX: Float = 0f
    private var maxTranslationY: Float = 0f
    private val FRICTION = 1.1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fling)
        flingAnimationX.addEndListener { _, _, _, _ ->
            if (flingAnimationY.isRunning) flingAnimationY.cancel()
        }

        flingAnimationY.addEndListener { _, _, _, _ ->
            if (flingAnimationX.isRunning) flingAnimationX.cancel()
        }

        drag.afterMeasured {
            flingAnimationX.setMinValue(0f)
                .setMaxValue(maxTranslationX)
                .friction = FRICTION
            flingAnimationY.setMinValue(0f)
                .setMaxValue(maxTranslationY)
                .friction = FRICTION
        }

        container.afterMeasured {
            maxTranslationX = (container.width - drag.width).toFloat()
            maxTranslationY = (container.height - drag.height).toFloat()
        }

        val gestureDetector = GestureDetector(this, gestureListener)

        drag.setOnTouchListener { _, motionEvent ->
            gestureDetector.onTouchEvent(motionEvent)
        }
    }

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            downEvent: MotionEvent,
            moveEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            flingAnimationX.setStartVelocity(velocityX)
            flingAnimationY.setStartVelocity(velocityY)

            flingAnimationX.start()
            flingAnimationY.start()
            return true
        }
    }
}