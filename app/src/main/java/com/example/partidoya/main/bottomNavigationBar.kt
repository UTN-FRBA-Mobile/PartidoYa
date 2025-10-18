package com.example.partidoya.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.partidoya.R

data class BarItem(val nombre: String, val logoSeleccionado: Int, val logoNoSeleccionado: Int)
@Composable
fun BottomNavigationBar(navController: NavController){
    NavigationBar (containerColor = Color.Black,
                    contentColor = Color.White,
                    modifier = Modifier.height(70.dp)){
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

        val barItems = arrayOf(
            BarItem("matches", R.drawable.logomispartidosseleccionado, R.drawable.logomispartidos),
            BarItem("home", R.drawable.logomenuseleccionado, R.drawable.logomenu),
            BarItem("profile", R.drawable.logoperfilseleccionado, R.drawable.logoperfil)
        )
        
        barItems.forEach { item ->
            NavigationBarItem(
                icon = { Image(painter =
                    if (currentDestination == item.nombre) {
                        painterResource(id = item.logoSeleccionado)
                    } else {
                        painterResource(id = item.logoNoSeleccionado)
                    },
                    contentDescription = item.nombre + "Icon",
                    modifier = Modifier.align(Alignment.CenterVertically).padding(top=10.dp)) },
                selected = currentDestination == item.nombre,
                onClick = {navController.navigate(item.nombre)},
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.Gray,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color.Gray,
                    unselectedTextColor = Color.White,
                    selectedIndicatorColor = Color.Transparent,
                    disabledIconColor = Color.Transparent,
                    disabledTextColor = Color.Transparent
                )
            )
        }
    }
}