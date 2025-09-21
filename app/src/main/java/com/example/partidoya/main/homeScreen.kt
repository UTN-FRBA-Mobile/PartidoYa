package com.example.partidoya.main

import com.example.partidoya.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController){
    Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.logoapp), contentDescription = "Logo de la App")
        Text(text = "PartidoYa!", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(30.dp))
        GlassCard({
            HomeButton(onClick = {navController.navigate("createMatch")}, text = "BUSCAR JUGADORES")
            Spacer(modifier = Modifier.height(30.dp))
            HomeButton(onClick = {navController.navigate("matches")}, text = "BUSCAR PARTIDOS")
        })
    }
}


@Composable
@Preview
fun HomeScreenPreview(){
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = "Logo de la App",
            modifier = Modifier.size(250.dp)
        )
        Text(
            text = "PartidoYa!", fontSize = 50.sp, modifier = Modifier.padding(30.dp),
            fontWeight = FontWeight.Black
        )
        GlassCard({
            HomeButton(onClick = {}, text = "BUSCAR JUGADORES")
            Spacer(modifier = Modifier.height(30.dp))
            HomeButton(onClick = {}, text = "BUSCAR PARTIDOS")
        })
    }
}