package com.example.partidoya.main

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun OSMMap() {
    val context = LocalContext.current

    // Datos de la cancha
    val lat = -34.588789
    val lng = -58.3758644
    val name = "Futbol Retiro"
    val address = "Padre Carlos Mujica 199\nHorarios: 08:00 - 24:00"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        // Contenedor del mapa con bordes redondeados y fondo
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray) // para ver los bordes
        ) {
            AndroidView(
                factory = { ctx ->
                    MapView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setMultiTouchControls(true)
                        val location = GeoPoint(lat, lng)
                        controller.setZoom(16.0)
                        controller.setCenter(location)

                        val marker = Marker(this)
                        marker.position = location
                        marker.title = name
                        marker.subDescription = address
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        marker.showInfoWindow()
                        overlays.add(marker)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OpenInGoogleMapsButton(lat = lat, lng = lng, name = name)
    }
}