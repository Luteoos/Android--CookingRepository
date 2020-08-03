package io.github.luteoos.cookrepo.di

import io.github.luteoos.cookrepo.adapters.RVAdapterRecipes
import io.github.luteoos.cookrepo.repository.RecipeRepository
import io.github.luteoos.cookrepo.repository.RecipeRepositoryInterface
import io.github.luteoos.cookrepo.utils.Session
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import io.github.luteoos.cookrepo.viewmodel.UserProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Add your DI here
 */
val sessionPreferenceModule = module {
    single { Session(androidContext()) }
}

val mainScreenModule = module {
    viewModel { MainScreenViewModel(get()) }
    factory { RVAdapterRecipes(null) }
    factory <RecipeRepositoryInterface> { RecipeRepository() }
}

val userProfileModule = module {
    viewModel { UserProfileViewModel() }
}

val koinModules = listOf(mainScreenModule, sessionPreferenceModule, userProfileModule)
