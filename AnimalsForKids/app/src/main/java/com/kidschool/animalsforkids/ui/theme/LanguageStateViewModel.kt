package com.kidschool.animalsforkids.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LanguageStateViewModel: ViewModel() {
    private val _globalLanguageVariable = mutableStateOf(true)
    val globalLanguageVariable: State<Boolean> = _globalLanguageVariable

    fun toggleGlobalLanguageVariable() {
        _globalLanguageVariable.value = !_globalLanguageVariable.value
    }
}