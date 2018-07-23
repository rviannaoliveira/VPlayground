package com.rviannaoliveira.vformmvvm.core

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun FragmentManager.replace(id: Int, fragment: Fragment) {
    this.beginTransaction()
            .replace(id, fragment)
            .commit()
}