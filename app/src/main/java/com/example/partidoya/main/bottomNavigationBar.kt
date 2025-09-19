package com.example.partidoya.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.partidoya.R

@Composable
fun BottomNavigationBar(navController: NavController){
    NavigationBar (containerColor = Color.Black,
                    contentColor = Color.White,
                    modifier = Modifier.height(70.dp)){
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationBarItem(
            icon = { Image(painter =
                if (currentDestination == "misPartidos")
                { painterResource(id = R.drawable.logomispartidosseleccionado) }
                else
                { painterResource(id = R.drawable.logomispartidos) },
                contentDescription = "homeIcon",
                modifier = Modifier.align(Alignment.CenterVertically).padding(top=10.dp)) },
            selected = currentDestination == "",
            onClick = {navController.navigate("")},
            colors = NavigationBarItemColors(
                selectedIconColor = Color.Gray,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.Gray,
                unselectedTextColor = Color.White,
                selectedIndicatorColor = Color.Gray,
                disabledIconColor = Color.White,
                disabledTextColor = Color.White
            )
        )
        NavigationBarItem(
            icon = { Image(painter =
                if (currentDestination == "home")
                { painterResource(id = R.drawable.logomenuseleccionado) }
                else
                { painterResource(id = R.drawable.logomenu) },
                contentDescription = "homeIcon",
                modifier = Modifier.align(Alignment.CenterVertically).padding(top=10.dp)) },
            selected = currentDestination == "home" ,
            onClick = {navController.navigate("home")},
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
        NavigationBarItem(
            icon = { Image(painter =
                if (currentDestination == "account")
                { painterResource(id = R.drawable.logoperfilseleccionado) }
                else
                { painterResource(id = R.drawable.logoperfil) },
                contentDescription = "accountIcon",
                modifier = Modifier.align(Alignment.CenterVertically).padding(top=10.dp)) },
            selected = currentDestination == "account" ,
            onClick = {navController.navigate("account")},
            colors = NavigationBarItemColors(
                selectedIconColor = Color.Gray,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.Gray,
                unselectedTextColor = Color.White,
                selectedIndicatorColor = Color.Gray,
                disabledIconColor = Color.White,
                disabledTextColor = Color.White
            )
        )
    }
}