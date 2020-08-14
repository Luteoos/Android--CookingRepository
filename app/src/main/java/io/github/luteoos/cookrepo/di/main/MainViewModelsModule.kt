package io.github.luteoos.cookrepo.di.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.github.luteoos.cookrepo.di.ViewModelKey
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import io.github.luteoos.cookrepo.viewmodel.UserProfileViewModel

@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserProfileViewModel): ViewModel
}
