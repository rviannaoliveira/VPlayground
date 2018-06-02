package com.rviannaoliveira.core

import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.squareup.picasso.Picasso


fun ImageView.imageDrawable(@DrawableRes drawable: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(context, drawable))
}

fun ImageView.load(path: String) {
    Picasso.with(this.context)
            .load(path)
            .into(this)
}

fun ImageView.load(path: String, @DrawableRes defaultIcon: Int) {
    Picasso.with(this.context)
            .load(path)
            .placeholder(defaultIcon)
            .into(this)
}

fun ImageView.load(@DrawableRes drawable: Int) {
    Picasso.with(this.context)
            .load(drawable)
            .into(this)
}
