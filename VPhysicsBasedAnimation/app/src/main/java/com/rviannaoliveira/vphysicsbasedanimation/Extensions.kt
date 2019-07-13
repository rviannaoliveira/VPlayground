package com.rviannaoliveira.vphysicsbasedanimationandroid

import android.support.animation.SpringAnimation
import android.view.View
import android.view.ViewTreeObserver

inline fun SpringAnimation.onUpdate(crossinline onUpdate: (value: Float) -> Unit): SpringAnimation {
    return this.addUpdateListener { _, value, _ ->
        onUpdate(value)
    }
}

inline fun <T : View> T.afterMeasured(crossinline func: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            func()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}