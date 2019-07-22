package com.rviannaoliveira.varchitecturecomponentsmvvm.extensions

import android.app.Application
import java.io.IOException
import java.nio.charset.Charset

fun Application.getJsonString(filename: String): String? {
    return try {
        val inputStream = this.assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charset.defaultCharset())
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
}