package com.example.partidoya.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeButton(text:String, onClick: ()->Unit){
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.width(322.dp).height(56.dp)){
        Text(text=text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun GlassCard(width: Int, height: Int, content: @Composable ColumnScope.() -> Unit){
    //Permite pasar contenido dinamico para insertar en la columna
    Box(modifier = Modifier.width(width.dp)
        .height(height.dp)
        .background(Color(0x33020202),
            shape = RoundedCornerShape(30.dp))){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content)
    }
}

@Composable
fun LabeledInput(label: String, icon: ImageVector){
    TextField(
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
        modifier = Modifier.width(322.dp)
                            .height(56.dp)
                            .border(1.dp, Color.White, shape = RoundedCornerShape(10.dp)),

    )
}

