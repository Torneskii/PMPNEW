package com.kidschool.animalsforkids


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.AnimalScreen.route) {
                composable(route = Screen.AnimalScreen.route) {
                    AnimalScreen(navController)
                }
                composable(route = Screen.AddAdnimalScreen.route) {
                    AddAnimalScreen(navController)
                }
            }

        }
    }
}

