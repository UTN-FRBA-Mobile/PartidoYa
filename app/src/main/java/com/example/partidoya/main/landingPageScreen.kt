package com.example.partidoya.main

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.partidoya.R

@Composable
fun LandingPageScreen(navController: NavController){
    Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.logoapp), contentDescription = "Logo de la App")
        Text(text = "PartidoYa!", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(30.dp))
        GlassCard(){
            HomeButton(onClick = {navController.navigate("logIn")}, text = "INICIAR SESIÓN")
            Spacer(modifier = Modifier.height(30.dp))
            HomeButton(onClick = {navController.navigate("newAccount")}, text = "REGISTRARSE")
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