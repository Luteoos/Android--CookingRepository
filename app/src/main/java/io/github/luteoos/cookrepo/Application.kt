package io.github.luteoos.cookrepo

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initRealm()
        if (BuildConfig.DEBUG)
            initDebugStuff()
    }

    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration
                .Builder()
                .compactOnLaunch()
                .schemaVersion(0)
                .build()
        )
    }

    private fun initDebugStuff() {
        Timber.plant(Timber.DebugTree())
        Timber.i("initDebug Policy")
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }
}
