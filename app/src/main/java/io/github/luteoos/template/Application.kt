package io.github.luteoos.template

import android.app.Application
import android.os.StrictMode
import com.luteoos.kotlin.mvvmbaselib.BuildConfig
import io.github.luteoos.template.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(koinModules)
        }
        if(BuildConfig.DEBUG)
            initDebugStuff()
    }

    private fun initDebugStuff() {
        Timber.plant(Timber.DebugTree())
        Timber.e("initDebugStuff")
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())
    }
}