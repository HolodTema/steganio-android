package com.terabyte.steganio.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}