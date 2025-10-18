package com.example.partidoya.Preferences

import android.content.Context

class Preferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE)

    companion object{
        private const val saveInCalendarKey = "saveInCalendar"
    }

    var saveInCalendar: Boolean
        get() = sharedPreferences.getBoolean(saveInCalendarKey, false)
        set(value) {
            sharedPreferences.edit().putBoolean(saveInCalendarKey,value).apply()
        }
}