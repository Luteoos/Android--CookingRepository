package io.github.luteoos.cookrepo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.luteoos.cookrepo.di.main.MainFragmentBuildersModule
import io.github.luteoos.cookrepo.di.main.MainModule
import io.github.luteoos.cookrepo.di.main.MainScope
import io.github.luteoos.cookrepo.di.main.MainViewModelsModule
import io.github.luteoos.cookrepo.di.splash.SplashScope
import io.github.luteoos.cookrepo.view.activity.MainScreenActivity
import io.github.luteoos.cookrepo.view.activity.SplashScreenActivity

@Module
abstract class ActivityBuildersModule {
    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainViewModelsModule::class,
            MainFragmentBuildersModule::class,
            MainModule::class
        ]
    )
    abstract fun contributeMainScreenActivity(): MainScreenActivity

    @SplashScope
    @ContributesAndroidInjector()
    abstract fun contributeSplashScreenActivity(): SplashScreenActivity
}
