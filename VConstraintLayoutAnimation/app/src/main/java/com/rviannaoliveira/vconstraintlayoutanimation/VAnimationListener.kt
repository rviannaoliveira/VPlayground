package com.rviannaoliveira.vconstraintlayoutanimation

import android.view.animation.Animation


interface VAnimationListener : Animation.AnimationListener{
    override fun onAnimationRepeat(animation: Animation?) {
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
    }
}