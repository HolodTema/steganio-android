package com.terabyte.steganio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    val liveDataTextContainer = MutableLiveData<String>()
    val liveDataTextSecret = MutableLiveData<String>()

    fun encrypt(textContainer: String, textSecret: String, successListener: () -> Unit, failureListener: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred =  async(Dispatchers.IO) {

            }
        }
    }

    class Factory(val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(application) as T
        }
    }
}