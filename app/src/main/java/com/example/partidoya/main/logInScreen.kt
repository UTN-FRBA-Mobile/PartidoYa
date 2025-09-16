package com.example.partidoya.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LogInScreen(navController: NavController){
    Column {
        Spacer(Modifier.size(200.dp))
        Text(text = "LOGIN")
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "HOME")
        }
    }
}