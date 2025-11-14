package com.example.partidoya.main

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.partidoya.ui.theme.InputColors
import com.example.partidoya.ui.theme.InputModifier
import com.example.partidoya.ui.theme.normalInputModifier
import com.example.partidoya.ui.theme.unwrap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.example.partidoya.Service.DistanceCalculator
import com.example.partidoya.domain.Cancha

/* Cosas que cambio ciro que estan en conflicto
<<<<<<< HEAD
import com.example.partidoya.domain.Option
=======
 */
import com.example.partidoya.domain.DetalleJugador
import com.example.partidoya.domain.Partido
import com.example.partidoya.domain.PartidoEquipo
import com.example.partidoya.domain.PartidoJugadores
import com.example.partidoya.viewModels.PartidosViewModel
import java.net.URI
import java.time.LocalDateTime
import java.util.Calendar
import java.util.concurrent.TimeUnit

@Composable
fun HomeButton(text:String, onClick:  ()->Unit){

    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(322.dp)
            .height(56.dp)){
        Text(text=text, style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun MiniButton(text: String,onClick: () -> Unit){
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = Modifier
            .widthIn(min = 80.dp)
            .height(32.dp)){
        Text(text=text, style = MaterialTheme.typography.bodyMedium)
    }

}

@Composable

fun GlassCard(spaceBetween: Dp = 0.dp, esDarkGray: Boolean = false, content: @Composable ColumnScope.() -> Unit){
    //Permite pasar contenido dinamico para insertar en la columna
    Box(modifier = Modifier
        .background(
            if (esDarkGray) Color.DarkGray else Color(0x33020202),
            shape = RoundedCornerShape(30.dp)
        )){
        Column(modifier = Modifier.padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(spaceBetween, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content)
    }
}

@Composable
fun GlassCardTitle(title: String){
    Text(text = title,
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        modifier = Modifier.padding(top = 30.dp))
    Spacer(Modifier.height(30.dp))
}

@Composable
fun LabeledInput(label: String, icon: ImageVector){
    TextField(
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White

        ),
        value = "",
        onValueChange = {},
        label = { Text(label) },
        singleLine = true,
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Password icon"
            )
        },
        modifier = Modifier
            .width(322.dp)
            .height(56.dp)
            .border(1.dp, Color.White, shape = RoundedCornerShape(10.dp)),

    )
}

@Composable
fun LabelOverInput(
    label: String? = null,
    icon: ImageVector? = null,
    onChange: (String) -> Unit,
    value: String
){
    Column {
        if(label!=null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        Spacer(Modifier.height(15.dp))
        TextField(
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White

            ),
            value = value,
            onValueChange = onChange,
            singleLine = true,
            trailingIcon =
                if(icon != null) {
                    {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Password icon"
                        )
                    }
                }else null,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White, shape = RoundedCornerShape(10.dp))
            )
    }
}

@Composable
fun OutlineLabelInput(
    label: String, placeholder: String,
    singleLine: Boolean,
    modifier: InputModifier, value: String,
    onValueChange: (String) -> Unit
){

    OutlinedTextField(
        colors = InputColors,
        textStyle = MaterialTheme.typography.bodyMedium,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = MaterialTheme.typography.bodyMedium) },
        placeholder = { Text(placeholder) },
        singleLine = singleLine,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.unwrap()

    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable

/* Cosas que cambio ciro que estan en conflicto
<<<<<<< HEAD
fun AutoCompleteInput(label: String, value: String, onValueChange: (Option) -> Unit, options: List<Option>) {
    //TODO: Esto tiene que salir de alguna api con las localidades
    /*val options = listOf(
        "Villa Luro, CABA",
        "Caballito, CABA",
        "Moron, Buenos Aires",
        "Retiro, CABA",
        "Villa Ortuza, CABA",
        "Santa Rita, CABA"
    )*/
=======
 */
fun AutoCompleteInput(label: String,value: String,onValueChange: (String) -> Unit, barrios: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf(value) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = textValue,
            colors = InputColors,
            shape = RoundedCornerShape(16.dp),
            onValueChange = { newValue ->
                textValue = newValue
                expanded = newValue.isNotEmpty()
            },
            modifier = normalInputModifier
                .unwrap()
                .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
            singleLine = true,
            label = { Text(label, style = MaterialTheme.typography.bodyMedium) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .heightIn(max = 200.dp)
        )
        {

/* Cosas que cambio ciro que estan en conflicto
<<<<<<< HEAD
            options
                .filter { it.label.contains(value, ignoreCase = false) }
=======
 */
            barrios.filter { it.contains(value, ignoreCase = false) }
                .forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, style = MaterialTheme.typography.bodyMedium) },
                        onClick = {
                            textValue = option
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }

        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchPlayersCard(partido: PartidoJugadores, viewModel: PartidosViewModel, ubicacion: Location?, onClickUbi: (Cancha?) -> Unit) {
    var mostrarAlerta by remember { mutableStateOf(false) }
    var posicionElegida by remember { mutableStateOf("") }
    val context = LocalContext.current

    val titulo = "BUSCANDO JUGADORES PARA ${partido.formato}"


    LaunchedEffect(posicionElegida) { //Solo se ejecuta al modificar la posicionElegida
        if (posicionElegida != "")
            viewModel.confirmarPartidoJugadores(partido, posicionElegida)
    }

    Box(
        modifier = Modifier
            .width(386.dp)
            .background(
                Color(0x33020202),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = titulo,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(Modifier.height(30.dp))

            GenericInfoMatch(partido, onClickUbi)

            MediumText("DISTANCIA: " + DistanceCalculator.distance(partido.cancha,ubicacion).toInt() + "KM")
            Spacer(Modifier.height(5.dp))
            MediumText("JUGADORES FALTANTES: " + partido.jugadoresFaltantes)
            Spacer(Modifier.height(5.dp))
            MediumText("POSICIONES: " + partido.posicionesFaltantes.joinToString(", "))
            Spacer(Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.Center
            ) {

                BasicButton("SUMARME", { mostrarAlerta=true })


                    Spacer(Modifier.weight(1F))

                    BasicButton("DESCARTAR", { viewModel.descartarPartido(partido) }, isSuccess = false)

                    if (mostrarAlerta) {
                        var posicionesOpciones: List<String> = partido.posicionesFaltantes.toList()

                        if(partido.jugadoresFaltantes > 1)
                            posicionesOpciones = posicionesOpciones + "COMODÍN"

                        SeleccionPosicion(
                            onDismiss = { mostrarAlerta = false },
                            posiciones = posicionesOpciones,
                            accion = { posicion -> posicionElegida = posicion })
                    }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchTeamCard(partido: PartidoEquipo, viewModel: PartidosViewModel, ubicacion: Location?, onClickUbi: (Cancha?)-> Unit){

    val context = LocalContext.current
    val titulo = "BUSCANDO EQUIPO PARA ${partido.formato}"

    Box(modifier = Modifier
        .width(386.dp)
        .background(
            Color(0x33020202),
            shape = RoundedCornerShape(30.dp)
        )){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)){
            Text(text = titulo,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(30.dp))

            GenericInfoMatch(partido, onClickUbi)

            MediumText("DISTANCIA: " + DistanceCalculator.distance(partido.cancha,ubicacion).toInt() + "KM")
            Spacer(Modifier.height(5.dp))

            Row (modifier = Modifier.fillMaxWidth(),
                Arrangement.Center){
                var texto: String

                BasicButton("CONFIRMAR", { viewModel.confirmarPartidoEquipo(partido) })

                Spacer(Modifier.weight(1F))

                BasicButton("DESCARTAR", { viewModel.descartarPartido(partido) }, isSuccess = false)
            }
        }
    }
}

@Composable
fun MyMatchPlayerCard(partido: PartidoJugadores, viewModel: PartidosViewModel, onClickUbi: (Cancha?) -> Unit){
    var titulo = if(partido.jugadoresFaltantes == 0) "COMPLETO" else "INCOMPLETO"
    val filtroOrgJug by viewModel.filtroOrgJug.collectAsState()
    var mostrarAlertaDetalleJugadores by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .width(386.dp)
            .background(
                Color(0x33020202),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = titulo,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = when(titulo){
                    "COMPLETO" -> Color(0xFF2E8B57)
                    else -> Color(0xFFD32F2F)
                },
                style = MaterialTheme.typography.titleSmall
            )

            GenericInfoMatch(partido, onClickUbi)
            MediumText("FORMATO: " + partido.formato)
            Spacer(Modifier.height(5.dp))

            Spacer(Modifier.height(5.dp))
            MediumText("JUGADORES FALTANTES: " + partido.jugadoresFaltantes)
            Spacer(Modifier.height(5.dp))

            if(filtroOrgJug == "Jugador") { //Si es organizador, no tiene posicion elegida
                MediumText("POSICION: " + viewModel.posicionElegida(partido))
                Spacer(Modifier.height(5.dp))
            }

            FooterMatch(partido, filtroOrgJug, viewModel, {mostrarAlertaDetalleJugadores= true})
        }
    }
    if (mostrarAlertaDetalleJugadores) {
        VisualizarDetalleJugadores (
            onDismiss = { mostrarAlertaDetalleJugadores = false },
            detalleJugadores = partido.detalleJugadores
        )
    }
}



@Composable
fun MyMatchTeamCard(partido: PartidoEquipo, viewModel: PartidosViewModel, onClickUbi: (Cancha?) -> Unit){
    var titulo = if(partido.hayRepresentante) "CONFIRMADO" else "SIN CONFIRMAR"
    var colorTitulo = if(partido.hayRepresentante) Color(0xFF2E8B57) else Color(0xFFD32F2F)
    val filtroOrgJug by viewModel.filtroOrgJug.collectAsState()
    var mostrarAlertaDetalleJugadores by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(386.dp)
            .background(
                Color(0x33020202),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = titulo,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = colorTitulo,
                style = MaterialTheme.typography.titleSmall
            )

            GenericInfoMatch(partido, onClickUbi)
            MediumText("FORMATO: " + partido.formato)
            Spacer(Modifier.height(5.dp))

            FooterMatch(partido, filtroOrgJug, viewModel, {mostrarAlertaDetalleJugadores=true})
        }
    }
    if (mostrarAlertaDetalleJugadores) {
        VisualizarDetalleJugadores (
            onDismiss = { mostrarAlertaDetalleJugadores = false },
            detalleJugadores = partido.detalleJugadores
        )
    }
}

@Composable
fun FooterMatch(partido: Partido, filtroOrgJug: String, viewModel: PartidosViewModel, accion: () -> Unit?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicButton("CANCELAR", { if(filtroOrgJug == "Jugador") viewModel.abandonarPartido(partido)
        else viewModel.cancelarPartido(partido) }, isSuccess = false, isEnabled = partido.puedeCancelar)

        if(filtroOrgJug != "Jugador" && partido.detalleJugadores != null && partido.detalleJugadores.isNotEmpty()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                    text = "Ver jugadores",
                    color = Color(0xFF1E88E5), // azul tipo link
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        accion()
                    }
                )
        }
    }
}


@Composable
fun GenericInfoMatch(partido: Partido, onClickUbi: (Cancha?) -> Unit){

    Row(modifier = Modifier.fillMaxWidth()) {
        MediumText("FECHA: " + partido.fecha)
        Spacer(Modifier.weight(1f))//Empuja el horario a la derecha
        MediumText("DIA: " + partido.dia)
    }
    Spacer(Modifier.height(5.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        MediumText("HORARIO: " + partido.horario)
        Spacer(Modifier.weight(1f))//Empuja el horario a la derecha
        MediumText("DURACIÓN: " + partido.duracion + " min")
    }
    Spacer(Modifier.height(5.dp))
    Text(
        text = "CANCHA: " + partido.cancha?.nombre,
        textDecoration = TextDecoration.Underline,
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xff3366cc),
        modifier = Modifier.clickable(
            enabled = true,
            onClick = { onClickUbi(partido.cancha) })
    )
    Spacer(Modifier.height(5.dp))
    MediumText("ZONA: " + partido.barrio)
    Spacer(Modifier.height(5.dp))
}

@Composable
fun MediumText(text: String){
    Text(text = text,style = MaterialTheme.typography.bodyMedium, color = Color.White)
}

@Composable
fun RatingComponent (value: Int) {
    var estrellasPintadas = 0;
    estrellasPintadas = if (value <= 0) {
        0;
    } else if (value <= 20) {
        1;
    } else if (value <= 40) {
        2;
    } else if (value <= 60) {
        3;
    } else if (value <= 80) {
        4;
    } else {
        5;
    }

    val totalEstrellas = 5;

    val estrellasSinPintar = totalEstrellas - estrellasPintadas;

    Row {
        for (i in 1..estrellasPintadas) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "STAR ICON WHITE $i",
                tint = Color.Yellow
            )
        }
        for (i in 1..estrellasSinPintar) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "STAR ICON GRAY $i",
                tint = Color.LightGray
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizarDetalleJugadores(onDismiss: ()-> Unit ,detalleJugadores: List<DetalleJugador>?){
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        content = {
            GlassCard (esDarkGray = true){
                Text(text = "Detalle Jugadores", style = MaterialTheme.typography.titleSmall, color = Color.White)
                Spacer(Modifier.height(10.dp))
                detalleJugadores?.forEach { detalleJugador ->
                    Spacer(Modifier.height(10.dp))
                    Text(detalleJugador.name.toString() + " " + detalleJugador.surname.toString(), style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Spacer(Modifier.height(10.dp))
                    Text(detalleJugador.celular.toString(), style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Spacer(Modifier.height(10.dp))
                    Text(detalleJugador.playStyle.toString(), style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Spacer(Modifier.height(10.dp))
                    RatingComponent(detalleJugador.reputacion?.toInt() ?: 0)
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(thickness = 5.dp, color = Color.White,modifier = Modifier.width(322.dp))
                }
                Spacer(Modifier.height(10.dp))
                Button(onClick = onDismiss) { Text("VOLVER", style = MaterialTheme.typography.bodyMedium) }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionPosicion(onDismiss: ()-> Unit, posiciones: List<String>?, accion: (String) -> Unit?){
        BasicAlertDialog(
            onDismissRequest = onDismiss,
            content = {GlassCard {
                Text(text = "Elige una posición", style = MaterialTheme.typography.titleSmall, color = Color.White)
                Spacer(Modifier.height(10.dp))
                posiciones?.forEach { posicion ->
                    Text(posicion,style = MaterialTheme.typography.bodyMedium, color = Color.White,
                            modifier = Modifier.clickable(onClick = {accion(posicion)
                                                                        onDismiss()
                            }))
                    Spacer(Modifier.height(10.dp))
                }
                Button(onClick = onDismiss) { Text("CANCELAR", style = MaterialTheme.typography.bodyMedium) }
            }
            }
        )
}

@Composable
fun ToastMatchCreated(){
    val context = LocalContext.current
    Toast.makeText(context, "Partido creado exitosamente", Toast.LENGTH_LONG).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun agendarEnCalendario(partido: Partido, context: Context){
    val fechaHora = LocalDateTime.of(partido.fecha, partido.horario)
    val fechaHoraInicio = Calendar.getInstance().run {
        set(
            fechaHora.year,
            fechaHora.monthValue-1,
            fechaHora.dayOfMonth,
            fechaHora.hour,
            fechaHora.minute
        )
        timeInMillis
    }

    val duracionEnMilis = TimeUnit.MINUTES.toMillis(partido.duracion.toLong())
    val fechaHoraFin = fechaHoraInicio + duracionEnMilis
    val ubicacion = "${partido.cancha?.direccion}"

    val intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fechaHoraInicio)
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fechaHoraFin)
        .putExtra(CalendarContract.Events.TITLE, "PARTIDO DE FUTBOL")
        .putExtra(CalendarContract.Events.DESCRIPTION, partido.formato)
        .putExtra(CalendarContract.Events.EVENT_LOCATION, ubicacion)

    context.startActivity(intent)
}

@Composable
fun Filtro(texto: String, onClick: () -> Unit){

    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White),
        border = BorderStroke(2.dp, Color.White)
    ){
        Text(text = texto, style = MaterialTheme.typography.bodyMedium)

    }
}

@Composable
fun BasicButton(text: String, handleClick: () -> Unit, isSuccess: Boolean = true, isEnabled: Boolean = true) {
    Button(modifier = Modifier
        .width(169.dp)
        .height(49.dp),
        onClick = { handleClick() },
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(if (isSuccess) 0xFF3C7440 else 0xFFA93838),
            contentColor = Color.White)){
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteInputBarrios(label: String,value: String,onValueChange: (String) -> Unit, barrios: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    Column() {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
        Spacer(Modifier.height(15.dp))
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

            TextField(
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedTrailingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White

                ),
                value = value,
                onValueChange = { newValue ->
                    onValueChange(newValue)
                    expanded = newValue.isNotEmpty()
                },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
                    .fillMaxWidth()
                    .border(1.dp, Color.White, shape = RoundedCornerShape(10.dp)),
                singleLine = true
            )


            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .heightIn(max = 200.dp)
            )
            {
                barrios.filter { it.contains(value, ignoreCase = false) }
                    .forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, style = MaterialTheme.typography.bodyMedium) },
                            onClick = {
                                onValueChange(option)
                                expanded = false
                            }
                        )
                    }

            }
        }
    }
}