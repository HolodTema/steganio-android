package com.terabyte.steganio.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.TextActivity
import com.terabyte.steganio.activity.LoginActivity
import com.terabyte.steganio.util.INTENT_KEY_LOGIN_ACTIVITY_MODE
import com.terabyte.steganio.util.INTENT_KEY_PIN_TO_CONFIRM
import com.terabyte.steganio.util.LOGIN_ACTIVITY_MODE_CONFIRM
import com.terabyte.steganio.util.LOGIN_ACTIVITY_MODE_CREATE
import com.terabyte.steganio.util.ShPreferencesHelper
import com.terabyte.steganio.util.showToast

class LoginViewModel(application: Application): AndroidViewModel(application) {
    val liveDataPIN = MutableLiveData("")
    val liveDataLoginText = MutableLiveData("Enter your PIN:")
    val liveDataMode = MutableLiveData("")
    private var pinToConfirm = ""

    val isBiometricAuthEnabled = ShPreferencesHelper.getBooleanFromShPreferences(application, ShPreferencesHelper.KEY_HAS_BIOMETRIC_AUTH)

    fun checkPIN(context: Context) {
        when (liveDataMode.value) {
            LOGIN_ACTIVITY_MODE_CREATE -> {
                val intent = Intent(context, LoginActivity::class.java)
                    .putExtra(INTENT_KEY_LOGIN_ACTIVITY_MODE, LOGIN_ACTIVITY_MODE_CONFIRM)
                    .putExtra(INTENT_KEY_PIN_TO_CONFIRM, liveDataPIN.value)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                context.startActivity(intent)
            }
            LOGIN_ACTIVITY_MODE_CONFIRM -> {
                if (liveDataPIN.value == pinToConfirm) {
                    ShPreferencesHelper.putStringToShPreferences(context, ShPreferencesHelper.KEY_PIN, pinToConfirm.hashCode().toString())
                    context.showToast("New PIN created successfully!")
                    startMainActivity(context)
                }
                else {
                    context.showToast("Cannot confirm new PIN. Create PIN again.")
                    val intent = Intent(context, LoginActivity::class.java)
                        .putExtra(INTENT_KEY_LOGIN_ACTIVITY_MODE, LOGIN_ACTIVITY_MODE_CREATE)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    context.startActivity(intent)
                }
            }
            else -> {
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
        }

    }

    fun startMainActivity(context: Context) {
        val intent = Intent(context, TextActivity::class.java)
        context.startActivity(intent)
    }

    fun setModeCreate() {
        liveDataMode.value = LOGIN_ACTIVITY_MODE_CREATE
        liveDataLoginText.value = "Create new PIN:"
    }

    fun setModeConfirm(pinToConfirm: String) {
        liveDataMode.value = LOGIN_ACTIVITY_MODE_CONFIRM
        this.pinToConfirm = pinToConfirm
        liveDataLoginText.value = "Confirm your PIN - enter new PIN again:"
    }

    class Factory(val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(application) as T
        }
    }
}