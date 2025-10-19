package com.example.partidoya

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.AndroidViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.partidoya.Service.RetrofitClient
import com.example.partidoya.main.BottomNavigationBar
import com.example.partidoya.main.CreateMatch
import com.example.partidoya.main.HomeScreen
import com.example.partidoya.main.LandingPageScreen
import com.example.partidoya.main.LogInScreen
import com.example.partidoya.main.ModifyAccountScreen
import com.example.partidoya.main.Matches
import com.example.partidoya.main.MyMatches
import com.example.partidoya.main.NewAccountScreen
import com.example.partidoya.main.OSMMap
import com.example.partidoya.main.ProfileScreen
import com.example.partidoya.ui.theme.PartidoYaTheme
import com.example.partidoya.viewModels.MainViewModel
import com.example.partidoya.viewModels.PartidosViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PartidoYaTheme {
                    App()
                }
            }
        }
    }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App() {
    val partidosViewModel: PartidosViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    val context = LocalContext.current
    RetrofitClient.init(context)
    Scaffold(containerColor = MaterialTheme.colorScheme.background, //color del background
             bottomBar = {if (shouldShowBottomBar(navController)){
                                BottomNavigationBar(navController)
             } })
    { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        )
            NavHost(navController = navController, startDestination = "landingPage") {
                composable("home") { HomeScreen(navController) }
                composable("logIn") { LogInScreen(navController) }
                composable("newAccount") { NewAccountScreen(navController) }
                composable("landingPage") { LandingPageScreen(navController) }
                composable("profile" ) { ProfileScreen(navController) }
                composable("matches") { Matches(partidosViewModel, mainViewModel) }
                composable("createMatch") { CreateMatch(partidosViewModel) }
                composable ("modifyProfile" ) { ModifyAccountScreen(navController) }
                composable ("myMatches" ) { MyMatches(partidosViewModel) }
            }
        }
}


@Composable
fun shouldShowBottomBar(navController: NavController): Boolean {
    //Para que se actualize cada vez que se cambia de pantalla
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = navBackStackEntry?.destination?.route

    val screensWithOutNavBar = listOf("logIn","newAccount","landingPage", "modifyProfile")

    return !screensWithOutNavBar.contains(currentScreen)

}