package com.example.partidoya.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NewAccountScreen(navController: NavController){
    var correoElectronico by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    var repetirContrasenia by remember { mutableStateOf("") }

    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        GlassCard({
            GlassCardTitle("CREAR CUENTA")
            LabelOverInput(
                label = "CORREO ELECTRÓNICO*",
                icon = Icons.Default.Email,
                onChange = { mail -> correoElectronico = mail},
                value = correoElectronico
            )
            Spacer(Modifier.height(15.dp))
            LabelOverInput(
                label = "CONTRASEÑA*",
                icon = Icons.Default.Lock,
                onChange = { cont -> contrasenia = cont},
                value = contrasenia
            )
            Spacer(Modifier.height(15.dp))
            LabelOverInput(
                label = "REPETIR CONTRASEÑA*",
                icon = Icons.Default.Lock,
                onChange = { cont -> repetirContrasenia = cont},
                value = repetirContrasenia
            )
            Spacer(Modifier.height(50.dp))
            HorizontalDivider(thickness = 5.dp, color = Color.White,modifier = Modifier.width(322.dp))
            Spacer(Modifier.height(50.dp))
            HomeButton("Continuar", onClick = {navController.navigate("home")})
            Spacer(Modifier.height(15.dp))
            HomeButton("Continuar con Google", onClick = {})
            Spacer(Modifier.height(30.dp))
            Text(text = "Ya tengo una cuenta",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = {navController.navigate("logIn")}))
        })
    }
}