package com.rviannaoliveira.varchitecturecomponentsmvvm.home.model

data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String,
    val hero: Boolean
) {
    fun toCharacter(): Character =
        Character(
            id = id,
            name = name,
            description = description,
            isHero = hero
        )
}

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val isHero: Boolean
)