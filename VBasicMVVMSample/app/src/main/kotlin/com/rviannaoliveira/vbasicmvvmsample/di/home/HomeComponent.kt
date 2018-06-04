package com.rviannaoliveira.vbasicmvvmsample.di.home

import android.support.v4.app.Fragment
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    fun plus(fragment: Fragment) : Fragment
}