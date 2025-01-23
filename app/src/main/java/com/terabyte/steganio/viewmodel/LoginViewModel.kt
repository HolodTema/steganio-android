package com.terabyte.steganio.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.MainActivity
import com.terabyte.steganio.util.ShPreferencesHelper

class LoginViewModel(application: Application): AndroidViewModel(application) {
    val liveDataPIN = MutableLiveData("")
    val liveDataLoginText = MutableLiveData("Enter your PIN:")

    fun checkPIN(context: Context) {
        val requiredHashedPIN = ShPreferencesHelper.getStringFromShPreferences(context, ShPreferencesHelper.KEY_PIN)
        if (requiredHashedPIN == null) {
            startMainActivity(context)
        }
        else if (requiredHashedPIN == liveDataPIN.value.hashCode().toString()) {
            startMainActivity(context)
        }
        else {
            liveDataPIN.value = ""
            liveDataLoginText.value = "Incorrect PIN. Try again:"
        }
    }

    fun startMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    class Factory(val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(application) as T
        }
    }
}