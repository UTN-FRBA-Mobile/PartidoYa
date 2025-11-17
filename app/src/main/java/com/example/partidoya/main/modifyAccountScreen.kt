package com.example.partidoya.main

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.partidoya.ui.theme.InputColors
import com.example.partidoya.ui.theme.largeInputModifier
import com.example.partidoya.ui.theme.normalInputModifier
import com.example.partidoya.ui.theme.unwrap
import com.example.partidoya.viewModels.ModifyProfileViewModel
import com.example.partidoya.viewModels.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModifyAccountScreen(navController: NavController, paddingValues: PaddingValues, horizontalPadding: Dp){
 ModifyAccountScreenContent(navController, paddingValues, horizontalPadding)
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ModifyAccountScreenPreview(){
    //ModifyAccountScreenContent()
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModifyAccountScreenContent(navController: NavController?  = null, paddingValues: PaddingValues, horizontalPadding: Dp, viewModel: ModifyProfileViewModel = viewModel<ModifyProfileViewModel>(), profileViewModel: ProfileViewModel = viewModel<ProfileViewModel>()  ){
    val profile by viewModel.profileData.collectAsState()
    val existingProfile by profileViewModel.profileData.collectAsState()
    val barrios by viewModel.barrios.collectAsState()
    val uiState = viewModel.uiState
    val context = LocalContext.current
    /* Cosas que cambio ciro que estan en conflicto
<<<<<<< HEAD
    val playStyles by viewModel.playStyles.collectAsState()
    val position by viewModel.positions.collectAsState()
    val barrios by viewModel.positions.collectAsState()
    val barriosOption by viewModel.barrioOptions.collectAsState(emptyList())
=======
     */
    var scrollState = rememberScrollState()

    LaunchedEffect (uiState.success) {
        if (uiState.success) {
            Toast.makeText(context, "Se actualizaron los datos del perfil", Toast.LENGTH_LONG).show()
            // Llama al callback de navegación
            navController?.navigate("home")
        }
    }

/* Cosas que cambio ciro que estan en conflicto
<<<<<<< HEAD
LaunchedEffect(Unit) {
    viewModel.cargarPlayStyles()
    viewModel.cargarPosiciones()
    viewModel.cargarBarrios()
}

Column (verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxSize()){
=======
 */

LaunchedEffect(Unit) {
    profileViewModel.obtenerDatosDelPerfil()
    viewModel.cargarBarrios()
}

LaunchedEffect(existingProfile) {
        existingProfile?.let { existingProfile ->
            viewModel.onProfileChanged<String> {
                copy(
                    name = existingProfile.name ?: "",
                    surname = existingProfile.surname ?: "",
                    userIdentifier = existingProfile.userIdentifier ?: "",
                    //playStyle = existingProfile.playStyle ?: "",
                    location = existingProfile.location ?: "",
                    celular = existingProfile.celular ?: "",
                    description = existingProfile.description ?: "",
                    preferedPosition = existingProfile.preferedPosition ?: ""
                )
            }
        }
}

/* Cosas que cambio ciro que estan en conflicto
<<<<<<< HEAD
        AutoCompleteInput(label = "Estilo de juego",   profile?.playStyle ?: "" ,
            onValueChange = {newPlayStyle-> viewModel.onProfileChanged<String> {copy(playStyle = newPlayStyle.key)}},
            options = playStyles)

        AutoCompleteInput(label = "Posicion preferida",
            value= profile?.preferedPosition ?: "" ,
            onValueChange = {newPreferedPosition-> viewModel.onProfileChanged<String> {copy(preferedPosition = newPreferedPosition.key)}},
            options = position
        )

        AutoCompleteInput(label = "Ubicacion",
            profile?.location ?: "",
            onValueChange = {newUbicacion-> viewModel.onProfileChanged<String> {copy(location = newUbicacion.key)}},
            options = barriosOption);
=======
 */
        var completo = profile?.name != "" && profile?.surname != "" &&
        profile?.userIdentifier != "" && profile?.preferedPosition != "" && profile?.location != "" &&
        profile?.celular != ""
        //profile?.playStyle != "" &&

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = horizontalPadding)
                .verticalScroll(scrollState)
        ) {

            GlassCard(spaceBetween = 8.dp) {
                Text(
                    text = "Completa tus datos",
                    fontSize = 30.sp, color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(10.dp))
                OutlineLabelInput(
                    label = "Nombre",
                    placeholder = "Pepe",
                    singleLine = true,
                    normalInputModifier,
                    profile?.name ?: "",
                    onValueChange = { newNombre ->
                        viewModel.onProfileChanged<String> {
                            copy(
                                name = newNombre
                            )
                        }
                    })
                OutlineLabelInput(
                    label = "Apellido",
                    placeholder = "Gonzales",
                    singleLine = true,
                    normalInputModifier,
                    profile?.surname ?: "",
                    onValueChange = { newApellido ->
                        viewModel.onProfileChanged<String> {
                            copy(
                                surname = newApellido
                            )
                        }
                    })
                //Ver donde se incluye
                //OutlineLabelInput(label = "Nombre de usuario", placeholder = "@PepZals",normalInputModifier,
                OutlineLabelInput(
                    label = "Nombre de usuario",
                    placeholder = "boquitaPasion",
                    singleLine = true,
                    normalInputModifier,
                    profile?.userIdentifier ?: "",
                    onValueChange = { newUsername ->
                        viewModel.onProfileChanged<String> {
                            copy(
                                userIdentifier = newUsername
                            )
                        }
                    })
                //  profile?.apellido ?: "" , onValueChange = {newUserName-> viewModel.onProfileChanged<String> {copy( = newApellido)}})

                OutlineLabelInput(
                    label = "Telefono",
                    placeholder = "11 1234 5678",
                    singleLine = true,
                    normalInputModifier,
                    profile?.celular ?: "",
                    onValueChange = { newCelular ->
                        if(newCelular.isDigitsOnly()) {
                            viewModel.onProfileChanged<String> {
                                copy(
                                    celular = newCelular
                                )
                            }
                        }
                    })

                /*DropDownEstiloJuego(seleccion = profile?.playStyle, onClick =  { newPlayStyle ->
                    viewModel.onProfileChanged<String> {
                        copy(
                            playStyle = newPlayStyle
                        )
                    }
                })*/

                DropDownPosicionFavorita (seleccion = profile?.preferedPosition, onClick =  { newPosition ->
                    viewModel.onProfileChanged<String> {
                        copy(
                            preferedPosition = newPosition
                        )
                    }
                })

                AutoCompleteInput(
                    label = "Ubicacion",
                    profile?.location ?: "",
                    onValueChange = { newUbicacion ->
                        viewModel.onProfileChanged<String> {
                            copy(
                                location = newUbicacion
                            )
                        }
                    },
                    barrios);

                OutlineLabelInput(
                    label = "Sobre vos",
                    placeholder = ".....",
                    singleLine = false,
                    largeInputModifier,
                    profile?.description ?: "",
                    onValueChange = { newPresentacion ->
                        viewModel.onProfileChanged<String> {
                            copy(
                                description = newPresentacion
                            )
                        }
                    })


                Spacer(Modifier.height(10.dp))

                Button(
                    onClick = {
                        if(barrios.contains(profile?.location))
                            viewModel.modificarPerfil()
                        else
                            Toast.makeText(context,"Error, ingrese un barrio de entre las opciones", Toast.LENGTH_LONG).show()},
                    enabled = completo,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(322.dp)
                        .height(56.dp)
                ) {
                    Text(text = "Confirmar datos", style = MaterialTheme.typography.bodyMedium)
                }
            }

        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownEstiloJuego(seleccion: String?, onClick: (String) -> Unit) {
val estilos = listOf("COMPETITIVO", "RECREATIVO");

var expanded by remember { mutableStateOf(false) } //si se expandió o no


ExposedDropdownMenuBox(
    expanded = expanded,
    onExpandedChange = { expanded = !expanded }
){
    OutlinedTextField(
        value = seleccion?:"",
        colors = InputColors,
        shape = RoundedCornerShape(16.dp),
        onValueChange = { newValue ->
            onClick(newValue)
            expanded = newValue.isNotEmpty()
        },
        modifier = normalInputModifier
            .unwrap()
            .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
        readOnly = true,
        label = { Text("Estilo de Juego", style = MaterialTheme.typography.bodyMedium) },
        placeholder = {Text("COMPETITIVO",style = MaterialTheme.typography.bodyMedium)}
    )
    ExposedDropdownMenu(
        expanded = expanded,
onDismissRequest = { expanded = false },
modifier = Modifier
    .heightIn(max = 200.dp)
)
{
    estilos.forEach { estilo ->
            DropdownMenuItem(
                text = { Text(estilo, style = MaterialTheme.typography.bodyMedium) },
                onClick = {onClick(estilo)
                        expanded = false}
            )
        }

}
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownPosicionFavorita(seleccion: String?, onClick: (String) -> Unit) {
    val posiciones = listOf("ARQUERO", "DEFENSOR", "MEDIOCAMPISTA", "DELANTERO");
    var expanded by remember { mutableStateOf(false) } //si se expandió o no

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ){
        OutlinedTextField(
            value = seleccion?:"",
            colors = InputColors,
            shape = RoundedCornerShape(16.dp),
            onValueChange = { newValue ->
                onClick(newValue)
                expanded = newValue.isNotEmpty()
            },
            modifier = normalInputModifier
                .unwrap()
                .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
            readOnly = true,
            label = { Text("Posición preferida", style = MaterialTheme.typography.bodyMedium) },
            placeholder = {Text("ARQUERO",style = MaterialTheme.typography.bodyMedium)}
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .heightIn(max = 200.dp)
        )
        {
            posiciones.forEach { posicion ->
                DropdownMenuItem(
                    text = { Text(posicion, style = MaterialTheme.typography.bodyMedium) },
                    onClick = {onClick(posicion)
                        expanded = false}
                )
            }

        }

    }
}
