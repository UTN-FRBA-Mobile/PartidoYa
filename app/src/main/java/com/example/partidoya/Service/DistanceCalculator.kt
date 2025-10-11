package com.example.partidoya.Service

import android.location.Location
import com.example.partidoya.domain.Cancha

object DistanceCalculator {
    fun distance(cancha: Cancha?, ubicacion: Location?): Float{

        var results = FloatArray(1)
        if(cancha!=null && ubicacion!=null)
            Location.distanceBetween(ubicacion.latitude,ubicacion.longitude, cancha.lat, cancha.lon, results)

        return results[0]/1000 //Para pasarlo a KM
    }
}