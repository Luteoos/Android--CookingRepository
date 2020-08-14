package io.github.luteoos.cookrepo.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import io.github.luteoos.cookrepo.viewmodel.factory.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory?): ViewModelProvider.Factory?
}
