package com.rviannaoliveira.core.presentation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun FragmentManager.replace(id: Int, fragment: Fragment) {
    this.beginTransaction()
            .replace(id, fragment)
            .commit()
}