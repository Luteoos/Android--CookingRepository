package io.github.luteoos.template.utils

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getRestDate(): String{
    return SimpleDateFormat(Parameters.REST_DATE, Locale.getDefault()).let {
        it.timeZone = TimeZone.getDefault()
        it.format(this.time)
    }
}

fun Calendar.getSDFdate(outputPattern: String = "yyyy-MM-dd HH:mm"): String{

    return SimpleDateFormat(outputPattern, Locale.getDefault()).let {
        it.timeZone = TimeZone.getDefault()
        it.format(this.time)
    }
}

fun String.toUUID() =
    UUID.fromString(this)