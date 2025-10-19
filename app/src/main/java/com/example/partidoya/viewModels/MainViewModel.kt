package com.example.partidoya.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.partidoya.Preferences.Preferences

class MainViewModel(application: Application): AndroidViewModel(application) {
    private var preferences = Preferences(getApplication())
    private val mutableSaveInCalendar: MutableLiveData<Boolean> = MutableLiveData(preferences.saveInCalendar)
    val saveInCalendar: LiveData<Boolean> get() = mutableSaveInCalendar


    fun toggleSaveInCalendar() {
        val newValue = !preferences.saveInCalendar
        preferences.saveInCalendar = newValue
        mutableSaveInCalendar.postValue(newValue)
    }



}