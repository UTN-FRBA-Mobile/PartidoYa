package com.example.partidoya.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.partidoya.R
import com.example.partidoya.ui.theme.largeInputModifier
import com.example.partidoya.ui.theme.normalInputModifier

@Composable
fun ModifyAccountScreen(navController: NavController){
 ModifyAccountScreenContent(navController)
}
@Preview
@Composable
fun ModifyAccountScreenPreview(){
    ModifyAccountScreenContent()
}
@Composable
fun ModifyAccountScreenContent(navController: NavController?  = null){
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()){

        GlassCard(spaceBetween = 8.dp){
            Text(text = "Completa tus datos",fontSize = 30.sp, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
            OutlineLabelInput(label = "Nombre", placeholder = "Pepe", normalInputModifier)
            OutlineLabelInput(label = "Apellido", placeholder = "Gonzales",normalInputModifier)
            OutlineLabelInput(label = "Nombre de usuario", placeholder = "@PepZals",normalInputModifier)
            AutoCompleteInput(label = "Ubicacion");
            OutlineLabelInput(label = "Sobre vos", placeholder = ".....", largeInputModifier)

            Spacer(Modifier.height(10.dp))
            HomeButton("Continuar", onClick = {navController?.navigate("home")?: Unit})
        }

    }
}

