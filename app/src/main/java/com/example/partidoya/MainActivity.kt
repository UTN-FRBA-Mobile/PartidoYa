package com.example.partidoya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.partidoya.main.HomeScreen
import com.example.partidoya.main.LandingPageScreen
import com.example.partidoya.main.LogInScreen
import com.example.partidoya.main.NewAccountScreen
import com.example.partidoya.ui.theme.PartidoYaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PartidoYaTheme {
                Scaffold(containerColor = MaterialTheme.colorScheme.background) //color del background
                { paddingValues ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        App()
                    }
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "landingPage") {
        composable("home") { HomeScreen(navController) }
        composable ("logIn" ) { LogInScreen(navController) }
        composable ("newAccount" ) { NewAccountScreen(navController) }
        composable ("landingPage" ) { LandingPageScreen(navController) }
    }
}

@Composable
fun BottomBar(){

}