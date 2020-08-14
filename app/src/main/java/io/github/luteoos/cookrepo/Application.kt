package io.github.luteoos.cookrepo

// import io.github.luteoos.cookrepo.di.koinModules
import android.os.StrictMode
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.luteoos.cookrepo.di.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class Application : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            if (BuildConfig.DEBUG)
//                androidLogger()
//            androidContext(this@Application)
//            modules(koinModules)
//        }
        initRealm()
        if (BuildConfig.DEBUG)
            initDebugStuff()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.builder().application(this).build()

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
