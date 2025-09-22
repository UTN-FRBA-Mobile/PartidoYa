package com.example.partidoya.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.partidoya.R

val lexend = FontFamily(Font(R.font.lexendvariable, FontWeight.Normal))
val holtwood = FontFamily(Font(R.font.holtwoodonescregular,FontWeight.Normal))

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle( //Para titulo de la app
        fontFamily = holtwood,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp),

    titleMedium = TextStyle( //Para titulos de tarjetas
        fontFamily = holtwood,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp),

    titleSmall = TextStyle( //Para titulos de partidos
        fontFamily = holtwood,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp),

    bodyMedium = TextStyle( //Para botones e inputs
        fontFamily = lexend,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


