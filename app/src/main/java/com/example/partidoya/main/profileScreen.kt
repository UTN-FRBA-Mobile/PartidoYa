package com.example.partidoya.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.partidoya.R
import com.example.partidoya.viewModels.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavController, paddingValues: PaddingValues, horizontalPadding: Dp, viewModel: ProfileViewModel = viewModel<ProfileViewModel>() ){
    val logoutData = viewModel.logoutData
    val profile by viewModel.profileData.collectAsState()

    LaunchedEffect(Unit) {viewModel.obtenerDatosDelPerfil() }

    if (logoutData.logoutSuccess) {
        navController.navigate("landingPage") {
            popUpTo("home") {
                inclusive = true
            }
        }
        return
    }

    if (profile == null || logoutData.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    } else {
        val profile = profile!!
        Container(
            navController,
            (profile.name) + " " + (profile.surname),
            profile.userIdentifier,
            profile.preferedPosition,
            profile.location,
            profile.playStyle,
            profile.description,
            profile.celular,
            profile.reputation,
            profile.toPlayMatches,
            profile.playedMatches,
            profile.canceledMatches,
            viewModel::logout,
            paddingValues,
            horizontalPadding
        )

    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    val nombre = "DAMIAN MARTINEZ";
    val usuario = "@dibu";
    val posicion = "ARQUERO";
    val ubicacion = "PALERMO";
    val modoJuego = "COMPETITIVO";
    val presentacion = "Damián Emiliano Martínez Romero (Mar del plata, 2 de septiembre de 1992), " +
            "más conocido como Dibu Martinez o simplemente Dibu, es un futbolista argentino " +
            "que juega en el Aston Villa de la Premier League.";

    //Container(null,nombre, usuario, posicion, ubicacion, modoJuego, presentacion, logout = {})
}

@Composable
fun Container(navController: NavController?=null,nombre: String, usuario: String, posicion: String, ubicacion: String, modoJuego: String, presentacion: String, celular:String, reputacion: Int, partidosPorJugar: Int, partidosJugados: Int, partidosCancelados: Int, logout: () -> Unit, paddingValues: PaddingValues, horizontalPadding: Dp){
    var expanded by remember { mutableStateOf(false) }
    var scrollState = rememberScrollState()
    var reputacionTipo = getTipoReputacion(reputacion)
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = horizontalPadding).verticalScroll(scrollState)) {
        GlassCard(){

                // 🔹 Button aligned to top end
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(8.dp)
                ) {
                    IconButton (onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Más acciones", tint = Color.White)
                    }
                    DropdownMenu (
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Modificar perfil") },
                            onClick = {
                                navController?.navigate("modifyProfile")
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar sesión") },
                            onClick = {
                                logout()
                                expanded = false
                            }
                        )
                    }
                }

            Column (verticalArrangement = Arrangement.spacedBy(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = nombre, fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "@$usuario", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(32.dp))

            // First version of icons + labels (too much text to be on the same line)
            /*Row (
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconLabel(posicion, Icons.Default.Star)
                IconLabel(ubicacion, Icons.Default.LocationOn)
                IconLabel(modoJuego, Icons.Default.Face)
            }*/

            // Second version with 2 lines
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row( // première ligne avec 2 champs
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconLabel(posicion, Icons.Default.Favorite)
                    Spacer(modifier = Modifier.width(30.dp)) // space between the 2 IconLabels
                    IconLabel(ubicacion, Icons.Default.LocationOn)
                }

                Spacer(Modifier.height(8.dp)) // space between the lines

                Row( // deuxième ligne avec le dernier champ
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconLabel(celular, Icons.Default.Phone)
                    Spacer(modifier = Modifier.width(30.dp))
                    IconLabel(modoJuego, Icons.Default.Face)
                }
            }

            Spacer(Modifier.height(32.dp)) // space between the lines
            Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Reputación: $reputacionTipo", fontSize = 14.sp, color = Color.White)
                RatingComponent(reputacion)
                Text(text = "Por jugar: $partidosPorJugar | Jugados: $partidosJugados | Cancelados: $partidosCancelados", fontSize = 14.sp, color = Color.White)
            }

            Spacer(Modifier.height(32.dp))

            if(presentacion != "") {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Text(text = "Sobre mi", fontSize = 14.sp, color = Color.White)
                    Box(
                        modifier = Modifier.width(364.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp))
                            .height(250.dp)
                    ) {
                        Box(modifier = Modifier.padding(16.dp)) {
                            Text(text = presentacion, fontSize = 14.sp, color = Color.White)
                        }
                    }
                }
            }

        }
    }
}

fun getTipoReputacion(value: Int): String {
    if (value >= 81) {
        return "Excelente"
    } else if (value >=61) {
        return "Muy Buena"
    } else if (value >=41) {
        return "Buena"
    }
    return "Regular"
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