package com.example.partidoya.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


//No soy programador asi que si algo de aca no les gusta cambienlo sin anestesia
@JvmInline
value class InputModifier(val modifier: Modifier)
//NOTE: No se que es este error
@Suppress("ModifierFactoryExtensionFunction")
val Modifier.asInput: InputModifier
get() = InputModifier(this)
fun InputModifier.unwrap(): Modifier = this.modifier

//NOTE: Esto tal vez tiene que tomar en cuenta las dimensiones del dispositivo
val normalInputModifier: InputModifier = Modifier.width(322.dp)
    .height(80.dp).asInput

val largeInputModifier: InputModifier = Modifier.width(322.dp)
    .height(200.dp)

    .asInput

