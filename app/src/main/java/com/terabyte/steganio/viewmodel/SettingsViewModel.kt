package com.terabyte.steganio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.util.ShPreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {
    val liveDataHasPIN = MutableLiveData(false)

    init {
        liveDataHasPIN.value = ShPreferencesHelper.isKeyInShPreferences(application, ShPreferencesHelper.KEY_PIN)
    }

    class Factory(val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(application) as T
        }
    }
}