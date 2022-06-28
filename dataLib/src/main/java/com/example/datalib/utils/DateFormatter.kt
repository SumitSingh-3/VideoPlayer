package com.example.datalib.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String {

    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val output = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    var d: Date? = null
    try {
        d = input.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    d?.let {
        return output.format(it)
    } ?: return String()
}
