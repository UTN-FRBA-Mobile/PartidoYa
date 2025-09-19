package com.example.partidoya.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LogInScreen(navController: NavController){
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        GlassCard(width = 364, height = 653) {
            GlassCardTitle("INICIAR SESIÓN")
            LabeledInput("CORREO ELECTRÓNICO", Icons.Default.Email)
            Spacer(Modifier.height(15.dp))
            LabeledInput("CONTRASEÑA", Icons.Default.Lock)
            Spacer(Modifier.height(50.dp))
            HorizontalDivider(thickness = 5.dp, color = Color.White,modifier = Modifier.width(322.dp))
            Spacer(Modifier.height(50.dp))
            HomeButton("Continuar", onClick = {})
            Spacer(Modifier.height(30.dp))
            Text(text = "¿Olvidaste tu contraseña?",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = {}))
        }
    }
}

@Preview
@Composable
fun logInScreenPreview(){
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        GlassCard(width = 364, height = 653) {
            GlassCardTitle("INICIAR SESIÓN")
            LabeledInput("CORREO ELECTRÓNICO", Icons.Default.Email)
            Spacer(Modifier.height(15.dp))
            LabeledInput("CONTRASEÑA", Icons.Default.Lock)
            Spacer(Modifier.height(50.dp))
            HorizontalDivider(thickness = 5.dp, color = Color.White,modifier = Modifier.width(322.dp))
            Spacer(Modifier.height(50.dp))
            HomeButton("Continuar", onClick = {})
            Spacer(Modifier.height(30.dp))
            Text(text = "¿Olvidaste tu contraseña?",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = {}))
        }
    }
}