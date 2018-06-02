package com.rviannaoliveira.core

import android.view.View

private val emptyOnComplete: (V: View) -> Unit = {}


var View.visible: Boolean
    get() = this.visibility == View.VISIBLE
    set(visible) {
        if (visible) this.show() else this.invisible()
    }

fun View.invisible() {
        this.visibility = View.INVISIBLE
}
fun <V : View> V.show(doIfNotVisible: (V: View) -> Unit = emptyOnComplete) {
        if (!visible) doIfNotVisible(this)
        this.visibility = View.VISIBLE
}

fun View.hide() {
        this.visibility = View.GONE
}