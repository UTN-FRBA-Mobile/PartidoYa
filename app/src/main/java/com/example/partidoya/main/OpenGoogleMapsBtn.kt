package com.example.partidoya.main

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun OpenInGoogleMapsButton(lat: Double, lng: Double, name: String) {
    val context = LocalContext.current
    Button(
        onClick = {
            val encodedName = Uri.encode(name)
            val url = "https://www.google.com/maps/search/?api=1&query=$lat,$lng($encodedName)"
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "No se pudo abrir Google Maps", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Abrir en Google Maps")
    }
}
