package io.github.luteoos.cookrepo.utils

import io.github.luteoos.cookrepo.data.realm.BaseRealmInterface
import io.realm.Realm
import io.realm.RealmObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

fun <T : BaseRealmInterface> T.create(authorName: String) {
    this.id = UUID.randomUUID().toString()
    this.author = authorName
    this.created = Calendar.getInstance().getSDFdate() // change to respect GMT
}

fun <T : RealmObject> Realm.getFirst(id: String, clazz: Class<T>): T? =
    this
        .where(clazz)
        .equalTo("id", id)
        .findFirst()

fun Calendar.getRestDate(): String {
    return SimpleDateFormat(Parameters.REST_DATE, Locale.getDefault()).let {
        it.timeZone = TimeZone.getDefault()
        it.format(this.time)
    }
}

fun Calendar.getSDFdate(outputPattern: String = "yyyy-MM-dd HH:mm"): String {

    return SimpleDateFormat(outputPattern, Locale.getDefault()).let {
        it.timeZone = TimeZone.getDefault()
        it.format(this.time)
    }
}

fun String.toUUID() =
    UUID.fromString(this)
