package com.rviannaoliveira.varchitecturecomponentsmvvm

import android.content.res.Resources
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.model.CharacterHero
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation.MainUiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainUiModelTest{
    private lateinit var uiModel : MainUiModel
    @Mock
    private lateinit var resources : Resources
    @Mock
    private lateinit var character : CharacterHero

    @Before
    fun setUp(){
        given(character.name).willReturn(name)
        uiModel = MainUiModel(resources,character)
    }

    @Test
    fun `should return hero when character is hero`(){
        val textHero = "ab"
        given(character.isHero).willReturn(true)
        given(resources.getString(R.string.yes_hero)).willReturn(textHero)

        assertEquals(uiModel.hero,textHero)
        verify(resources).getString(R.string.yes_hero)
    }


    @Test
    fun `should return is not hero when character is not hero`(){
        val textHero = "abc"
        given(character.isHero).willReturn(false)
        given(resources.getString(R.string.no_hero)).willReturn(textHero)

        assertEquals(uiModel.hero,textHero)
        verify(resources).getString(R.string.no_hero)
    }

    @Test
    fun `should return name from the character`(){
        assertEquals(uiModel.name,name)
    }
}