package com.terabyte.steganio.util

import android.content.Context

object ShPreferencesHelper {
    fun getStringFromShPreferences(context: Context, key: String): String? {
        return context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getString(key, null)
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

    fun deleteStringFromShPreferences(context: Context, key: String) {
        context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(key)
            .apply()
    }

    const val SH_PREFERENCES_NAME = "shPreferences"
    const val KEY_PIN = "shPreferencesKeyPIN"
}