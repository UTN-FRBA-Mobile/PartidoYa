package com.example.partidoya.main

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.partidoya.Preferences.Preferences
import com.example.partidoya.viewModels.NewAccountViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewAccountScreen(navController: NavController, horizontalPadding: Dp, viewModel: NewAccountViewModel = viewModel<NewAccountViewModel>()){
    val uiState = viewModel.uiState
    val context = LocalContext.current

    @RequiresApi(Build.VERSION_CODES.O)
    fun handleContinuar () {
        if (uiState.password != uiState.passwordRepeatable) {
            Toast.makeText(context, "Las contraseñas deben ser iguales", Toast.LENGTH_LONG).show()
        } else {
            viewModel.register()
        }
    }

    LaunchedEffect (uiState.registerSuccess) {
        if (uiState.registerSuccess) {
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
            val preferences = Preferences(context)
            preferences.saveToken(uiState.token.toString())
            // Llama al callback de navegación
            navController.navigate("modifyProfile")
        }
    }

    LaunchedEffect (uiState.error) {
        if (uiState.error != null) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_LONG).show()
        }
    }

    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(horizontal = horizontalPadding)) {
        GlassCard(content ={
            GlassCardTitle("CREAR CUENTA")
            LabelOverInput(
                label = "CORREO ELECTRÓNICO*",
                icon = Icons.Default.Email,
                onChange = { mail -> viewModel.onEmailChange(mail)},
                value = uiState.email
            )
            Spacer(Modifier.height(15.dp))
            PasswordTextField(
                label = "CONTRASEÑA*",
                onChange = { cont -> viewModel.onPasswordChange(cont)},
                value = uiState.password
            )
            Spacer(Modifier.height(15.dp))
            PasswordTextField(
                label = "REPETIR CONTRASEÑA*",
                onChange = { cont -> viewModel.onPasswordRepeatableChange(cont)},
                value = uiState.passwordRepeatable
            )
            Spacer(Modifier.height(50.dp))
            HorizontalDivider(thickness = 5.dp, color = Color.White,modifier = Modifier.width(322.dp))
            Spacer(Modifier.height(50.dp))
            HomeButton("Continuar", onClick = {handleContinuar()})
            Spacer(Modifier.height(30.dp))
            Text(text = "Ya tengo una cuenta",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = {navController.navigate("logIn")}))
        })
    }
}