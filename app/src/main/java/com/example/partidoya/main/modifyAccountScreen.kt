package com.example.partidoya.main

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.partidoya.R
import com.example.partidoya.ui.theme.largeInputModifier
import com.example.partidoya.ui.theme.normalInputModifier
import com.example.partidoya.viewModels.ModifyProfileViewModel
import com.example.partidoya.viewModels.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModifyAccountScreen(navController: NavController){
 ModifyAccountScreenContent(navController)
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ModifyAccountScreenPreview(){
    ModifyAccountScreenContent()
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModifyAccountScreenContent(navController: NavController?  = null,viewModel: ModifyProfileViewModel = viewModel<ModifyProfileViewModel>() ){
    val profile by viewModel.profileData.collectAsState()
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val playStyles by viewModel.playStyles.collectAsState()
    val position by viewModel.positions.collectAsState()
    val barrios by viewModel.positions.collectAsState()
    val barriosOption by viewModel.barrioOptions.collectAsState(emptyList())
    LaunchedEffect (uiState.success) {
        if (uiState.success) {
            Toast.makeText(context, "Se actualizaron los datos del perfil", Toast.LENGTH_LONG).show()
            // Llama al callback de navegación
            navController?.navigate("home")
        }
    }
    LaunchedEffect(Unit) {
        viewModel.cargarPlayStyles()
        viewModel.cargarPosiciones()
        viewModel.cargarBarrios()
    }

    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()){

        GlassCard(spaceBetween = 8.dp){

            Text(text = "Completa tus datos",
                fontSize = 30.sp, color = Color.White,
                style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
            OutlineLabelInput(label = "Nombre", placeholder = "Pepe", singleLine = true, normalInputModifier,
                profile?.name ?: "" ,
                onValueChange = {newNombre-> viewModel.onProfileChanged<String> {copy(name = newNombre)}} )
            OutlineLabelInput(label = "Apellido", placeholder = "Gonzales", singleLine = true,normalInputModifier, profile?.surname ?: "" ,
                onValueChange = {newApellido-> viewModel.onProfileChanged<String> {copy(surname = newApellido)}})
            //Ver donde se incluye
            //OutlineLabelInput(label = "Nombre de usuario", placeholder = "@PepZals",normalInputModifier,
            OutlineLabelInput(label = "Nombre de usuario", placeholder = "@boquitaPasion", singleLine = true,normalInputModifier, profile?.userIdentifier ?: "" ,
                onValueChange = {newUsername-> viewModel.onProfileChanged<String> {copy(userIdentifier = newUsername)}})
              //  profile?.apellido ?: "" , onValueChange = {newUserName-> viewModel.onProfileChanged<String> {copy( = newApellido)}})

            AutoCompleteInput(label = "Estilo de juego",   profile?.playStyle ?: "" ,
                onValueChange = {newPlayStyle-> viewModel.onProfileChanged<String> {copy(playStyle = newPlayStyle)}},
                options = playStyles)

            AutoCompleteInput(label = "Posicion preferida",
                value= profile?.preferedPosition ?: "" ,
                onValueChange = {newPreferedPosition-> viewModel.onProfileChanged<String> {copy(preferedPosition = newPreferedPosition)}},
                options = position
            )

            AutoCompleteInput(label = "Ubicacion",
                profile?.location ?: "",
                onValueChange = {newUbicacion-> viewModel.onProfileChanged<String> {copy(location = newUbicacion)}},
                options = barriosOption);

            OutlineLabelInput(label = "Sobre vos", placeholder = ".....", singleLine = false, largeInputModifier,
                profile?.description ?: "" ,
                onValueChange = {newPresentacion-> viewModel.onProfileChanged<String> {copy(description = newPresentacion)}})


            Spacer(Modifier.height(10.dp))
            HomeButton("Confirmar datos", onClick = {viewModel.modificarPerfil()})
        }

    }


}

