package com.example.partidoya.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun ModifyAccountScreenContent(navController: NavController?  = null,viewModel: ModifyProfileViewModel = viewModel<ModifyProfileViewModel>() ){
    val profile by viewModel.profileData.collectAsState()
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()){

        GlassCard(spaceBetween = 8.dp){
            Text(text = "Completa tus datos",fontSize = 30.sp, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
            OutlineLabelInput(label = "Nombre", placeholder = "Pepe", normalInputModifier,
                profile?.nombre ?: "" , onValueChange = {newNombre-> viewModel.onProfileChanged<String> {copy(nombre = newNombre)}} )
            OutlineLabelInput(label = "Apellido", placeholder = "Gonzales",normalInputModifier,
                profile?.apellido ?: "" , onValueChange = {newApellido-> viewModel.onProfileChanged<String> {copy(apellido = newApellido)}})
            //Ver donde se incluye
            //OutlineLabelInput(label = "Nombre de usuario", placeholder = "@PepZals",normalInputModifier,
              //  profile?.apellido ?: "" , onValueChange = {newUserName-> viewModel.onProfileChanged<String> {copy( = newApellido)}})
            AutoCompleteInput(label = "Ubicacion", profile?.ubicacion ?: "", onValueChange = {newUbicacion-> viewModel.onProfileChanged<String> {copy(ubicacion = newUbicacion)}});
            OutlineLabelInput(label = "Sobre vos", placeholder = ".....", largeInputModifier,
                profile?.presentacion ?: "" , onValueChange = {newPresentacion-> viewModel.onProfileChanged<String> {copy(presentacion = newPresentacion)}})


            Spacer(Modifier.height(10.dp))
            HomeButton("Continuar", onClick = {navController?.navigate("home")?: Unit})
        }

    }
}

