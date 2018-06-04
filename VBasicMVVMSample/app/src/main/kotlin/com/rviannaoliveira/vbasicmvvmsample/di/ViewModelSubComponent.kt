package com.rviannaoliveira.vbasicmvvmsample.di

import dagger.Subcomponent

@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder{
       fun build() : ViewModelSubComponent
    }
}