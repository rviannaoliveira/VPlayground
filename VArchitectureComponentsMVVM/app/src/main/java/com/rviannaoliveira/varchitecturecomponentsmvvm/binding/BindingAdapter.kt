package com.rviannaoliveira.varchitecturecomponentsmvvm.binding

import android.view.View
import androidx.databinding.BindingAdapter


object BindingAdapters {
    @BindingAdapter("visible")
    @JvmStatic
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}