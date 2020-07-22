package io.github.luteoos.cookrepo

import android.app.Application
import android.os.StrictMode
import com.luteoos.kotlin.mvvmbaselib.BuildConfig
import io.github.luteoos.cookrepo.di.koinModules
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            if(io.github.luteoos.cookrepo.BuildConfig.DEBUG)
                androidLogger()
            androidContext(this@Application)
            modules(koinModules)
        }
        initRealm()
        if(BuildConfig.DEBUG)
            initDebugStuff()
    }

    private fun initRealm(){
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration
            .Builder()
            .compactOnLaunch()
            .build())
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
                .build())
    }
}