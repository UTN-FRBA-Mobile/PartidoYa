package com.example.partidoya.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ModifyAccountScreen(navController: NavController){
 ModifyAccountScreenContent()
}
@Preview
@Composable
fun ModifyAccountScreenPreview(){
    ModifyAccountScreenContent()
}
@Composable
fun ModifyAccountScreenContent(){
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        GlassCard({
            Text(text = "Completa tus datos",fontSize = 30.sp, color = Color.White)
            Spacer(Modifier.height(10.dp))
            OutlineLabelInput(label = "Nombre", placeholder = "Pepe")
            OutlineLabelInput(label = "Apellido", placeholder = "Gonzales")
            OutlineLabelInput(label = "Nombre de usuario", placeholder = "@PepZals")
        })
    }
}

