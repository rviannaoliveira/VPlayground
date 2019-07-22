package com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation

import android.content.res.Resources
import com.rviannaoliveira.varchitecturecomponentsmvvm.R
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.model.Character

data class MainUiModel(
    private val resources: Resources,
    private val character: Character
) {
    val name = character.name
    val description = character.description
    val isHero = character.isHero

    val hero: String
        get() {
            return if (character.isHero) {
                resources.getString(R.string.yes_hero)
            } else {
                resources.getString(R.string.no_hero)
            }
        }
}