package com.example.partidoya.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.partidoya.Preferences.Preferences
import com.example.partidoya.R
import com.example.partidoya.viewModels.GoogleAuth
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LandingPageScreen(navController: NavController, horizontalPadding: Dp,googleAuth: GoogleAuth = viewModel<GoogleAuth>()){

    val uiState = googleAuth.uiState
    val activity = LocalActivity.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val preferences = Preferences(context)

    //TODO: Meter esto en una funcion para no repetir codigo
    LaunchedEffect (uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            Toast.makeText(context, "Login exitoso", Toast.LENGTH_LONG).show()
            // Llama al callback de navegación
            val preferences = Preferences(context)
            preferences.saveToken(uiState.token.toString())
            if (uiState.register == true)
                navController.navigate("modifyProfile")
            else navController.navigate("home")
        }
    }
    LaunchedEffect (uiState.error) {
        if (uiState.error != null) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        preferences.clearToken()
    }

    Column (modifier = Modifier.fillMaxSize().padding(horizontal = horizontalPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.logoapp), contentDescription = "Logo de la App")
        Text(text = "PartidoYa!", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(30.dp))
        GlassCard(){
            HomeButton(onClick = {navController.navigate("logIn")}, text = "INICIAR SESIÓN")
            Spacer(modifier = Modifier.height(30.dp))
            HomeButton(onClick = {navController.navigate("newAccount")}, text = "REGISTRARSE")
            Spacer(modifier = Modifier.height(30.dp))
            HomeButton(onClick = {
                scope.launch {
                googleAuth(activity as ComponentActivity, googleAuth)
            }}, text = "INICIAR SESIÓN CON GOOGLE")
        }
    }
}

@Preview
@Composable
fun LandingPageScreenPreview(){
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.logoapp), contentDescription = "Logo de la App")
        Text(text = "PartidoYa!", fontSize = 50.sp, modifier = Modifier.padding(30.dp))
        GlassCard(){
            HomeButton(onClick = {}, text = "INICIAR SESIÓN")
            Spacer(modifier = Modifier.height(30.dp))
            HomeButton(onClick = {}, text = "REGISTRARSE")
        }
    }
}