package com.terabyte.steganio.util

import com.chaquo.python.Python

object PythonHelper {

    fun getText(): String {
        return Python.getInstance()
            .getModule("main")
            .callAttr("get_text", "Artem")
            .toString()
    }
}