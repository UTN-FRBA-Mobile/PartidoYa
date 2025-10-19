package com.example.partidoya.main

import android.preference.PreferenceManager
import android.view.ViewGroup
import android.widget.Button
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.partidoya.domain.Cancha
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory

@Composable
fun OSMMap(cancha: Cancha, onCancel: () -> Unit) {
    val context = LocalContext.current

    val lat = cancha.lat
    val lng = cancha.lon
    val name = cancha.nombre
    val address = cancha.direccion


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
                    //Necesario para que pueda almacernar y obtener las "Tiles"
                    Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

                    MapView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setMultiTouchControls(true)
                        setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
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

        Button(onClick = onCancel){
            Text(text = "cancelar", style = MaterialTheme.typography.bodyMedium, color = Color.White,
                textDecoration = TextDecoration.Underline, fontStyle = FontStyle.Italic)
        }
    }
}