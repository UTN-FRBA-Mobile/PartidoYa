package com.example.partidoya.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.partidoya.R

@Composable
fun ProfileScreen(navController: NavController){
    val nombre = "DAMIAN MARTINEZ";
    val usuario = "@dibu";
    val posicion = "ARQUERO";
    val ubicacion = "PALERMO";
    val modoJuego = "COMPETITIVO";
    val presentacion = "Damián Emiliano Martínez Romero (Mar del plata, 2 de septiembre de 1992), " +
            "más conocido como Dibu Martinez o simplemente Dibu, es un futbolista argentino " +
            "que juega en el Aston Villa de la Premier League.";

    Container(nombre, usuario, posicion, ubicacion, modoJuego, presentacion)
}

@Composable
fun Container(nombre: String, usuario: String, posicion: String, ubicacion: String, modoJuego: String, presentacion: String){
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        GlassCard(width = 364, height = 794) {
            Column (verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box (modifier = Modifier
                    .size(200.dp),
                    contentAlignment = Alignment.Center) {
                    Image(painter = painterResource(id = R.drawable.fotodibu), contentDescription = "Foto perfil",  contentScale = ContentScale.Crop,
                        modifier = Modifier.size(192.dp).clip(CircleShape))
                }
                Text(text = nombre, fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = usuario, fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(50.dp))
            Row (
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconLabel(posicion, Icons.Default.Star)
                IconLabel(ubicacion, Icons.Default.LocationOn)
                IconLabel(modoJuego, Icons.Default.Face)
            }
            Spacer(Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                Text(text = "Sobre mi", fontSize = 14.sp, color = Color.White)
                Box (modifier = Modifier.width(364.dp).border(2.dp, Color.White, shape = RoundedCornerShape(16.dp)).height(250.dp)) {
                    Box (modifier = Modifier.padding(16.dp)) {
                        Text(text = presentacion, fontSize = 14.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun IconLabel (label: String, icon: ImageVector) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = "$label ICON",
            tint = Color.White
        )
        Text(text = label, fontSize = 14.sp, color = Color.White)
    }
}