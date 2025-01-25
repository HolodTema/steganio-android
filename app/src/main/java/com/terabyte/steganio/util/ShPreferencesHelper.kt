package com.terabyte.steganio.util

import android.content.Context

object ShPreferencesHelper {
    fun getStringFromShPreferences(context: Context, key: String): String? {
        return context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getString(key, null)
    }

    fun getBooleanFromShPreferences(context: Context, key: String): Boolean {
        return context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getBoolean(key, false)
    }

    fun isKeyInShPreferences(context: Context, key: String): Boolean {
        return context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .contains(key)
    }

    fun putStringToShPreferences(context: Context, key: String, value: String) {
        context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(key, value)
            .apply()
    }

    fun putBooleanToShPreferences(context: Context, key: String, value: Boolean) {
        context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    fun deleteStringFromShPreferences(context: Context, key: String) {
        context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(key)
            .apply()
    }

    const val SH_PREFERENCES_NAME = "shPreferences"
    const val KEY_PIN = "shPreferencesKeyPIN"
    const val KEY_HAS_SPLASH_TEXT_BLOCK = "shPreferencesKeyHasSplashTextBlock"
    const val KEY_HAS_DARK_THEME = "shPreferencesKeyHasDarkTheme"
    const val KEY_HAS_BIOMETRIC_AUTH = "shPreferencesKeyHasBiometricAuth"
}