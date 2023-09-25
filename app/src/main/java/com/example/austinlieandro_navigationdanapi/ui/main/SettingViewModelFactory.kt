package com.example.austinlieandro_navigationdanapi.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.austinlieandro_navigationdanapi.utils.SettingPreferences
import com.example.austinlieandro_navigationdanapi.utils.dataStore

class SettingViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        private var instance: SettingViewModelFactory? = null

        fun getInstance(context: Context): SettingViewModelFactory {
            return instance ?: synchronized(this) {
                val preferences = SettingPreferences.getInstance(context.dataStore)
                SettingViewModelFactory(preferences).also {
                    instance = it
                }
            }
        }
    }

}