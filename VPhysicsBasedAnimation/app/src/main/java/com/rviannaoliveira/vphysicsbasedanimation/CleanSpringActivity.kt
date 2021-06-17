package com.rviannaoliveira.vphysicsbasedanimation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import kotlinx.android.synthetic.main.activity_clean.*

@SuppressLint("SetTextI18n")
class CleanSpringActivity : AppCompatActivity() {
    private var flingAnimation : FlingAnimation? = null
    private var startSquareX : Float = 0.0f
    private var springAnimation : SpringAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clean)

        springAnimation = SpringAnimation(square, DynamicAnimation.ROTATION_X)
        startSquareX = square.translationX
        flingAnimation = FlingAnimation(square, DynamicAnimation.TRANSLATION_X)

        button.setOnClickListener {
            animated()
        }

        velocity.setOnSeekBarChangeListener(object : MySeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = if (velocity.progress == 0) 1 else velocity.progress
                velocityText.text = "velocity value: $value"
            }
        })

        friction.setOnSeekBarChangeListener(object : MySeekBarChangeListener(){
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = if (friction.progress == 0) 1 else friction.progress
                frictionText.text = "friction value: $value"
            }
        })

    }

    private fun animated(){
        val springForce = SpringForce().apply {
            finalPosition = 180f
            dampingRatio = 0.2f
            stiffness = SpringForce.STIFFNESS_MEDIUM

        }
        springAnimation?.run {
            setStartVelocity(3f)
            spring = springForce
            start()
        }
    }

    private fun animatedWithParamVelocityAndFriction(){
        val velocity = if (velocity.progress == 0) 1.toFloat() else velocity.progress * 50.toFloat()

        val pixelPerSecond = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            velocity, resources.displayMetrics
        )

        val friction = if (friction.progress == 0) 0.01f else friction.progress / 100f

        flingAnimation!!.setStartVelocity(pixelPerSecond)
            .setFriction(friction)
            .addEndListener { _, _, _, _ ->
                square.translationX = startSquareX
            }
            .start()
    }
}