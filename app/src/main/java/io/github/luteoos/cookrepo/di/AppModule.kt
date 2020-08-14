package io.github.luteoos.cookrepo.di

import dagger.Module
import dagger.Provides
import io.github.luteoos.cookrepo.Application
import io.github.luteoos.cookrepo.utils.Session
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSession(application: Application) = Session(application)
}
