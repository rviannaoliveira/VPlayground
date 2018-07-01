package com.rviannaoliveira.vformmvvm.core

import android.databinding.BindingAdapter
import android.support.annotation.StringRes
import android.support.design.widget.TextInputLayout

class ErrorBinder {

    companion object {
        @BindingAdapter("error")
        @JvmStatic fun setError(view: TextInputLayout, @StringRes resId: Int?) {
            if (resId == null) {
                view.isErrorEnabled = false
            } else {
                view.error = view.context.getString(resId)
                view.isErrorEnabled = true
            }
        }
    }
}