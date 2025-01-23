package com.terabyte.steganio.util

import android.content.Context

object ShPreferencesHelper {
    fun getStringFromShPreferences(context: Context, key: String): String? {
        return context.getSharedPreferences(SH_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getString(key, null)
    }

    const val SH_PREFERENCES_NAME = "shPreferences"
    const val KEY_PIN = "shPreferencesKeyPIN"
}