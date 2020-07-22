package io.github.luteoos.cookrepo.utils

import io.realm.Realm
import io.realm.RealmObject
import java.text.SimpleDateFormat
import java.util.*

fun <T : RealmObject> Realm.getFirst(uuid: String, clazz: Class<T>): T? =
        this
                .where(clazz)
                .equalTo("id", uuid)
                .findFirst()

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