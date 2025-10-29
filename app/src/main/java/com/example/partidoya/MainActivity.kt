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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    val horizontalPadding = 10.dp
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
                composable("home") { HomeScreen(navController, horizontalPadding) }
                composable("logIn") { LogInScreen(navController, horizontalPadding) }
                composable("newAccount") { NewAccountScreen(navController, horizontalPadding) }
                composable("landingPage") { LandingPageScreen(navController, horizontalPadding) }
                composable("profile" ) { ProfileScreen(navController,paddingValues,horizontalPadding) }
                composable("matches") { Matches(partidosViewModel, mainViewModel, paddingValues, horizontalPadding) }
                composable("createMatch") { CreateMatch(partidosViewModel,paddingValues,horizontalPadding) }
                composable ("modifyProfile" ) { ModifyAccountScreen(navController,paddingValues,horizontalPadding) }
                composable ("myMatches" ) { MyMatches(partidosViewModel,paddingValues, horizontalPadding ) }
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