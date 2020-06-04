package io.github.luteoos.template.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import io.github.luteoos.template.view.activity.SplashScreenActivity

class Session(context: Context) {
    private val USER_UUID = "USER_UUID"
    private val USERNAME = "USERNAME"
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)


    var username: String
        get() {
            return preferences.getString(USERNAME, Parameters.EMPTY_USERNAME) ?: Parameters.EMPTY_USERNAME
        }
        set(value) {
            preferences.edit().putString(USERNAME, value).apply()
        }

    fun logout(context: Context) {
        preferences.edit().clear().apply()
        val intent = Intent(context, SplashScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}