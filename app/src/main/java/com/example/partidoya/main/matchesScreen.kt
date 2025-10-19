package com.example.partidoya.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import com.example.partidoya.domain.Cancha
import com.example.partidoya.domain.PartidoEquipo
import com.example.partidoya.domain.PartidoJugadores
import com.example.partidoya.viewModels.MainViewModel
import com.example.partidoya.viewModels.PartidosViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Matches(viewModel: PartidosViewModel, mainViewModel: MainViewModel){
    val partidos by viewModel.partidos.collectAsState()
    val filtroJugEqui by viewModel.filtroJugEqui.collectAsState()
    val context = LocalContext.current
    var isGranted by remember { mutableStateOf(false) }
    var ubicacion by remember { mutableStateOf<Location?>(null) }
    var partidoConfirmado = viewModel.partidoConfirmado.collectAsState().value
    var saveInCalendar = mainViewModel.saveInCalendar.observeAsState(false).value
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var viewMap by remember { mutableStateOf(false) }
    var canchaConsultada by remember { mutableStateOf<Cancha?>(null) }


    //SI NO TENGO PERMISO PARA ACCEDER A SU UBICACION SE LO PIDO
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {granted ->
            if(granted){
                isGranted = true
            }else{
                isGranted = false
            }
        }
    )

    LaunchedEffect(filtroJugEqui){
        when(filtroJugEqui){
            "Jugadores" -> viewModel.cargarPartidosJugadores()
            "Equipo" -> viewModel.cargarPartidosEquipo()
        }
    }

    LaunchedEffect(partidoConfirmado) {
        if(saveInCalendar && partidoConfirmado != null)
                agendarEnCalendario(partidoConfirmado, context)

        viewModel.eliminarPartidoConfirmado()
    }


    LaunchedEffect(Unit) {

       /* when(filtroJugEqui){
            "Jugadores" -> viewModel.cargarPartidosJugadores()
            "Equipo" -> viewModel.cargarPartidosEquipo()
        }*/

        //VER SI TENGO ACCESO A SU UBICACION
        //Consultar si ya tengo permiso para acceder a la ubicacion
        isGranted = ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        //Si no tengo el permiso consultarlo
        if(!isGranted){
            launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    if(isGranted) {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    Log.d("UBICACION","BUSCANDO")
                    ubicacion = location
                    Log.d("UBICACION","${location.latitude} + ${location.longitude}")
                } else {
                    Log.e("UBICACION", "NO SE PUDO OBTENER LA UBICACION")
                }
            }
    }

    if(viewMap) {
        canchaConsultada?.let { cancha ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA000000))
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                OSMMap(canchaConsultada!!, { viewMap = false })
            }
        }
    }


    Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(bottom = 70.dp, top = 30.dp, start = 10.dp, end = 10.dp)){

        GlassCardTitle("PARTIDOS")
        if(isGranted && ubicacion == null) { //Mientras intenta definir la ubicacion
                Text(text = "CALCULANDO LA UBICACIÓN...", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        }else {

            Row (verticalAlignment = Alignment.CenterVertically){
                Filtro(filtroJugEqui, {viewModel.alternarFiltroJugEqui()})
                Spacer(Modifier.width(5.dp))
                Text("Agendar partidos en el calendario: ", color = Color.White,style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.weight(1F))
                Switch(
                    checked = saveInCalendar,
                    onCheckedChange = { mainViewModel.toggleSaveInCalendar() },
                    colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFF3C7440))
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(partidos) { partido ->
                    when (partido) {
                        is PartidoJugadores -> MatchPlayersCard(partido, viewModel, ubicacion, { cancha -> canchaConsultada = cancha
                                                                                                                        viewMap = true })
                        is PartidoEquipo -> MatchTeamCard(partido, viewModel, ubicacion, {cancha -> canchaConsultada = cancha
                                                                                                                viewMap = true})
                    }

                    Spacer(Modifier.height(15.dp))
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyMatches(viewModel: PartidosViewModel){
    val filtroJugEqui by viewModel.filtroJugEqui.collectAsState()
    var viewMap by remember { mutableStateOf(false) }
    var canchaConsultada by remember { mutableStateOf<Cancha?>(null) }
    val partidos by viewModel.misPartidos.collectAsState()

    LaunchedEffect(filtroJugEqui){
        when(filtroJugEqui){
            "Jugadores" -> viewModel.cargarMisPartidosJugadores()
            "Equipo" -> viewModel.cargarMisPartidosEquipo()
        }
    }

    if(viewMap) {
        canchaConsultada?.let { cancha ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA000000))
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                OSMMap(canchaConsultada!!, { viewMap = false })
            }
        }
    }


        Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(bottom = 70.dp, top = 30.dp, start = 10.dp, end = 10.dp)) {

            GlassCardTitle("MIS PARTIDOS")

            Filtro(filtroJugEqui, { viewModel.alternarFiltroJugEqui() })

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(partidos) { partido ->
                    when (partido) {
                        is PartidoJugadores -> MyMatchPlayerCard(partido, viewModel, { cancha ->
                            canchaConsultada = cancha
                            viewMap = true
                        })

                        is PartidoEquipo -> MyMatchTeamCard(partido, viewModel, { cancha ->
                            canchaConsultada = cancha
                            viewMap = true
                        })
                    }

                    Spacer(Modifier.height(15.dp))
                }
            }
        }
}


@RequiresApi(Build.VERSION_CODES.O) //Necesario para el LocalTime
@Composable
fun CreateMatch(viewModel: PartidosViewModel) {
    var horarioSeleccionado by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var fechaSeleccionada by remember { mutableStateOf(LocalDate.now()) }
    var duracionDefinida by remember { mutableStateOf("") }
    var formatoSeleccionado by remember { mutableStateOf("") }
    var busquedaSeleccionada by remember { mutableStateOf("") }
    var posicionesSeleccionadas = remember { mutableStateListOf<String>() }
    var canchaDefinida by remember { mutableStateOf<Cancha?>(null) }
    var barrioDefinido by remember { mutableStateOf("-") }
    var cantJugadoresFalatantesDefinido by remember { mutableStateOf("") }
    var scrollState = rememberScrollState()
    var cantJugadoresFaltantes by remember { mutableStateOf(0) }
    var partidoCreadoConExito by remember { mutableStateOf(false) }


    //Formato para traducir los dias a español
    val format = DateTimeFormatter.ofPattern("EEEE", Locale.forLanguageTag("es-ES"))

    var diaSeleccionado = fechaSeleccionada.format(format).uppercase()
    var completo = false
    var datosGeneralesCompletos = duracionDefinida != "" && formatoSeleccionado != "" && busquedaSeleccionada != "" &&
                                    barrioDefinido != "" && canchaDefinida != null

    LaunchedEffect(Unit) { //Se ejecuta una unica vez al cargar la pantalla
        viewModel.cargarCanchas()
    }

    LaunchedEffect(barrioDefinido) { //Se ejecuta solo cuando cambia el barrio
        viewModel.canchasPorBarrio(barrioDefinido)
        canchaDefinida = null
    }


    LaunchedEffect(cantJugadoresFalatantesDefinido) { //Se ejecuta solo cuando cambia la cant de jugadores faltantes
        posicionesSeleccionadas.clear()
        cantJugadoresFaltantes = cantJugadoresFalatantesDefinido.toIntOrNull() ?: 0
        repeat(cantJugadoresFaltantes){posicionesSeleccionadas.add("")}
    }


    if(partidoCreadoConExito){
        ToastMatchCreated()
        partidoCreadoConExito = false
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(bottom = 70.dp, top = 30.dp, start = 10.dp, end = 10.dp).verticalScroll(scrollState)
    ) {
        GlassCard(){
            GlassCardTitle("CREAR PARTIDO")
            Row (modifier = Modifier.fillMaxWidth()){
                Column{
                    Text(
                        text = "FECHA",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(Modifier.height(15.dp))
                    DateSelection(
                        fechaSeleccionada,
                        onClick = { fecha -> fechaSeleccionada = fecha })
                }
                Spacer(Modifier.width(15.dp))
                Column{
                    Text(text = "DIA", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Spacer(Modifier.height(15.dp))
                    Box(
                        modifier = Modifier
                            .height(56.dp)
                            .border(1.dp, Color.White, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = diaSeleccionado,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(15.dp))
            Row {
                Column {
                    Text(
                        text = "HORARIO",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(Modifier.height(15.dp))
                    TimeSelection(
                        horarioSeleccionado,
                        onClick = { horario -> horarioSeleccionado = horario })
                }
                Spacer(Modifier.width(15.dp))
                Column {
                    LabelOverInput(label = "DURACIÓN(min)", onChange = { dur -> duracionDefinida = dur}, value = duracionDefinida)
                }
            }
            Spacer(Modifier.height(15.dp))
            Text(
                text = "FORMATO",
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Left
            )
            Spacer(Modifier.height(15.dp))
            Column {
                Row {
                    MatchFormatButton(
                        "FUTBOL 5",
                        formatoSeleccionado == "FUTBOL 5",
                        { formatoSeleccionado = "FUTBOL 5" })
                    Spacer(Modifier.width(15.dp))
                    MatchFormatButton(
                        "FUTBOL 8",
                        formatoSeleccionado == "FUTBOL 8",
                        { formatoSeleccionado = "FUTBOL 8" })
                }
                Spacer(Modifier.height(15.dp))
                Row {
                    MatchFormatButton(
                        "FUTBOL 7",
                        formatoSeleccionado == "FUTBOL 7",
                        { formatoSeleccionado = "FUTBOL 7" })
                    Spacer(Modifier.width(15.dp))
                    MatchFormatButton(
                        "FUTBOL 11",
                        formatoSeleccionado == "FUTBOL 11",
                        { formatoSeleccionado = "FUTBOL 11" })
                }
            }
                Spacer(Modifier.height(15.dp))
                Spacer(Modifier.height(15.dp))
               // LabelOverInput(label = "BARRIO", onChange = { barrio -> barrioDefinido = barrio}, value = barrioDefinido)
                Text(
                    text = "BARRIO",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left
                )
                Spacer(Modifier.height(10.dp))
                DropDownBarrios(viewModel = viewModel, seleccion = barrioDefinido, onClick =  { barrio -> barrioDefinido = barrio})

                Spacer(Modifier.height(15.dp))

                //LabelOverInput(label = "CANCHA", onChange = { cancha -> canchaDefinida = cancha}, value = canchaDefinida)
                Text(
                    text = "CANCHA",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left
                )
                Spacer(Modifier.height(10.dp))
                DropDownFootballFields(viewModel = viewModel, seleccion = canchaDefinida, onClick =  { cancha -> canchaDefinida = cancha})

                 Spacer(Modifier.height(15.dp))
                Text(
                    text = "BUSCAR",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left
                )
                Spacer(Modifier.height(15.dp))
                Row {
                    MatchFormatButton(
                        "EQUIPO",
                        busquedaSeleccionada == "EQUIPO",
                        { busquedaSeleccionada = "EQUIPO" })
                    Spacer(Modifier.width(15.dp))
                    MatchFormatButton(
                        "JUGADORES",
                        busquedaSeleccionada == "JUGADORES",
                        { busquedaSeleccionada = "JUGADORES" })
                }
                Spacer(Modifier.height(15.dp))
                if (busquedaSeleccionada == "JUGADORES") {

                    completo= datosGeneralesCompletos &&
                                cantJugadoresFalatantesDefinido != "" &&
                                !posicionesSeleccionadas.any { posicion -> posicion == "" }



                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "JUGADORES FALTANTES",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Spacer(Modifier.width(15.dp))
                        LabelOverInput(onChange = { cant -> cantJugadoresFalatantesDefinido = cant}, value = cantJugadoresFalatantesDefinido)
                    }

                    Spacer(Modifier.height(15.dp))



                        if(cantJugadoresFaltantes > 0){
                            Column (modifier = Modifier.fillMaxWidth()) {
                                repeat(cantJugadoresFaltantes) { index ->
                                    Row (modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start){
                                        Text("POSICIÓN ${index + 1}:",
                                            color = Color.White,
                                            style = MaterialTheme.typography.bodyMedium,
                                            textAlign = TextAlign.Left)
                                        Spacer(Modifier.width(15.dp))
                                        DropDownPositions(formato = formatoSeleccionado,
                                                            seleccion = posicionesSeleccionadas[index],
                                                            onClick = {posicion -> posicionesSeleccionadas[index] = posicion})
                                    }
                                }
                            }
                        }
                }else if(busquedaSeleccionada == "EQUIPO"){
                    completo = datosGeneralesCompletos
                }

                Button(colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3C7440),
                            contentColor = Color.White),
                        enabled = completo,
                        onClick = {
                            if (busquedaSeleccionada == "JUGADORES") {
                                    val listaAux = posicionesSeleccionadas.toList() //Para persistir la informacion cuando se reinicia el estado
                                    viewModel.crearPartidoJugadores(
                                        fechaSeleccionada,
                                        diaSeleccionado,
                                        horarioSeleccionado,
                                        duracionDefinida.toIntOrNull() ?: 0,
                                        formatoSeleccionado,
                                        busquedaSeleccionada,
                                        canchaDefinida,
                                        barrioDefinido,
                                        cantJugadoresFaltantes,
                                        listaAux
                                    )

                            } else {

                                    viewModel.crearPartidoEquipo(
                                        fechaSeleccionada,
                                        diaSeleccionado,
                                        horarioSeleccionado,
                                        duracionDefinida.toIntOrNull() ?: 0,
                                        formatoSeleccionado,
                                        canchaDefinida,
                                        barrioDefinido
                                    )

                            }

                            //TODO corroborar que se haya creado el partido exitosamente en el backend
                            partidoCreadoConExito = true


                            fechaSeleccionada = LocalDate.now()
                            horarioSeleccionado = LocalTime.of(0,0)
                            duracionDefinida = ""
                            formatoSeleccionado = ""
                            busquedaSeleccionada = ""
                            canchaDefinida = null
                            barrioDefinido = ""
                            cantJugadoresFalatantesDefinido = ""
                        }


                ) {
                    Text(text = "CREAR", style = MaterialTheme.typography.bodyMedium)
                }
        }
    }
}

@Composable
fun DropDownFootballFields(viewModel: PartidosViewModel, seleccion: Cancha?, onClick: (Cancha) -> Unit) {
    val canchas by viewModel.canchasFiltradasPorBarrio.collectAsState()

    var expanded by remember { mutableStateOf(false) } //si se expandió o no

    Box(modifier = Modifier.fillMaxWidth()) {
        DropdownButton(seleccion?.nombre ?: "-", { expanded = true })

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            canchas?.forEach { cancha ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = cancha.nombre,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onClick(cancha)
                        expanded = false
                    })

            }


        }
    }
}

@Composable
fun DropDownBarrios(viewModel: PartidosViewModel, seleccion: String?, onClick: (String) -> Unit) {
    val barrios by viewModel.barrios.collectAsState()

    var expanded by remember { mutableStateOf(false) } //si se expandió o no

    Box(modifier = Modifier.fillMaxWidth()) {
        DropdownButton(seleccion ?: "-", { expanded = true })

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            barrios.forEach { barrio ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = barrio,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onClick(barrio)
                        expanded = false
                    })
            }
        }
    }
}

@Composable
fun DropDownPositions(formato: String, seleccion: String,onClick: (String)-> Unit) {
    val posiciones = when (formato){
        "FUTBOL 5" -> listOf("SIN DEFINIR","ARQUERO","DEFENSOR","DELANTERO")
        "FUTBOL 7" -> listOf("SIN DEFINIR","ARQUERO","DEFENSOR","MEDIOCAMPISTA","DELANTERO")
        "FUTBOL 8" -> listOf("SIN DEFINIR","ARQUERO","DEFENSOR","MEDIOCAMPISTA","DELANTERO")
        "FUTBOL 11" -> listOf("SIN DEFINIR","ARQUERO","DEFENSOR CENTRAL", "LATERAL","MEDIOCAMPISTA DEFENSIVO", "MEDIOCAMPISTA OFENSIVO","EXTREMO","DELANTERO CENTRO")
        else -> emptyList()
    }

    var expanded by remember { mutableStateOf(false) } //si se expandió o no

    Box(modifier = Modifier.fillMaxWidth()) {
        DropdownButton(seleccion,{expanded=true})

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            posiciones.forEach { posicion ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = posicion,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onClick(posicion)
                        expanded = false
                    })
            }
        }
    }
}

@Composable
fun DropdownButton(text: String, onClick: () -> Unit){
    Button(onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White),
            border = BorderStroke(1.dp, color = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()) {
        Text(text = text)
    }
}

@Composable
fun MatchFormatButton(text: String, selected: Boolean, onClick: ()->Unit){
    Button(modifier = Modifier.width(160.dp).height(35.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if(selected) Color(0xFF3C7440) else Color.Transparent,
                contentColor = Color.White),
            border = BorderStroke(1.dp, color = Color.White),
            shape = RoundedCornerShape(8.dp)
    ) {
        Text(text=text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSelection(horario: LocalTime, onClick: (LocalTime) -> Unit){
    val context = LocalContext.current
    var horarioSeleccionado: LocalTime

    Box(Modifier.height(56.dp).border(color = Color.White, width = 1.dp, shape = RoundedCornerShape(8.dp))) {
        Row {
            Text(
                modifier = Modifier.padding(10.dp),
                text = horario.toString(),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                onClick = {
                    TimePickerDialog(
                        context, //el contexto de Android (necesario para abrir el diálogo)

                        // Callback que se ejecuta cuando el usuario elige una hora
                        { _, hour, minute ->
                            horarioSeleccionado = LocalTime.of(hour, minute)
                            onClick(horarioSeleccionado)
                        },

                        horario.hour,    // hora inicial
                        horario.minute,  // minuto inicial
                        true
                    ).show()
                }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "selectTime"
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateSelection(fecha: LocalDate, onClick: (LocalDate) -> Unit){
    val context = LocalContext.current
    var fechaSeleccionada: LocalDate

    Box(Modifier.height(56.dp).border(color = Color.White, width = 1.dp, shape = RoundedCornerShape(8.dp))) {
        Row {
            Text(
                modifier = Modifier.padding(10.dp),
                text = fecha.toString(),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                onClick = {
                    DatePickerDialog(
                        context, //el contexto de Android (necesario para abrir el diálogo)

                        // Callback que se ejecuta cuando el usuario elige una fecha
                        { _, year, month, day ->
                            fechaSeleccionada = LocalDate.of(year,month + 1,day)
                            onClick(fechaSeleccionada)
                        },

                        fecha.year,
                        fecha.monthValue - 1,
                        fecha.dayOfMonth
                    ).show()
                }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "selectTime"
                )
            }
        }
    }

}