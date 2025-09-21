package com.example.partidoya.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.partidoya.domain.Partido

@Composable
fun HomeButton(text:String, onClick: ()->Unit){

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
fun GlassCard(content: @Composable ColumnScope.() -> Unit){
    //Permite pasar contenido dinamico para insertar en la columna
    Box(modifier = Modifier
        .background(
            Color(0x33020202),
            shape = RoundedCornerShape(30.dp)
        )){
        Column(modifier = Modifier.padding(30.dp),
            verticalArrangement = Arrangement.Center,
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
    Spacer(Modifier.height(70.dp))
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
                .width(322.dp)
                .height(56.dp)
                .border(1.dp, Color.White, shape = RoundedCornerShape(10.dp)),

            )
    }
}

@Composable
fun OutlineLabelInput(label: String, placeholder: String){

    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        textStyle = MaterialTheme.typography.bodyMedium,
        value = text,
        onValueChange = { newValue -> text = newValue },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = false
    )
}

@Composable
fun MatchCard(partido: Partido){
    val titulo = partido.formato + " " + partido.tipo
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
            Row (modifier = Modifier.fillMaxWidth()){
                MediumText("DIA: " + partido.dia)
                Spacer(Modifier.height(5.dp))
                Spacer(Modifier.weight(1f))//Empuja el horario a la derecha
                MediumText("HORARIO: " + partido.horario)


            }
            Spacer(Modifier.height(5.dp))
            MediumText("ZONA: " + partido.zona)
            Spacer(Modifier.height(5.dp))
            MediumText("CANCHA: " + partido.cancha)
            Spacer(Modifier.height(5.dp))
            MediumText("DISTANCIA: " + partido.distancia + "KM")
            Spacer(Modifier.height(5.dp))
            MediumText("JUGADORES FALTANTES: " + partido.jugadoresFaltantes)
            Spacer(Modifier.height(5.dp))
            MediumText("POSICIONES: " + partido.posiciones)
            Spacer(Modifier.height(40.dp))
            Row (modifier = Modifier.fillMaxWidth(),
                Arrangement.Center){
                MatchButton("SUMARME",Color(0xFF3C7440), Color.White)
                Spacer(Modifier.weight(1F))
                MatchButton("DESCARTAR",Color(0xFFA93838), Color.White)
            }
        }
    }
}

@Composable
fun MediumText(text: String){
    Text(text = text,style = MaterialTheme.typography.bodyMedium, color = Color.White)
}

@Composable
fun MatchButton(text: String, containerColor: Color, contentColor: Color){
    Button(modifier = Modifier
        .width(169.dp)
        .height(49.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor)){
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}