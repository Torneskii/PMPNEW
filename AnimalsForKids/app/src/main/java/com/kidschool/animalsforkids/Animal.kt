package com.kidschool.animalsforkids

import androidx.compose.ui.text.font.FontWeight

data class Animal(
    val name: Pair<String, String>,
    val firstFact: Pair<String, String>,
    val secondFact: Pair<String, String>,
    val description: Pair<String, String>,
    val continent: Pair<String, String>
)
