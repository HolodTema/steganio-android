package com.terabyte.steganio.viewmodel

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.TextActivity
import com.terabyte.steganio.activity.LoginActivity
import com.terabyte.steganio.util.SPLASH_SCREEN_TIME_MILLS
import com.terabyte.steganio.util.ShPreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel(application: Application): AndroidViewModel(application) {
    val liveDataAlarm = MutableLiveData(false)

    init {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                delay(SPLASH_SCREEN_TIME_MILLS)
            }
            deferred.await()
            liveDataAlarm.value = true
        }
    }

    class Factory(val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SplashScreenViewModel(application) as T
        }
    }
}