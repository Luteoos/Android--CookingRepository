package io.github.luteoos.cookrepo.di

import io.github.luteoos.cookrepo.adapters.RVAdapterRecipeCrumbs
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

val recipeModule = module {
    viewModel { MainScreenViewModel(get()) }
    factory <RecipeRepositoryInterface> { RecipeRepository() }
    factory { RVAdapterRecipeCrumbs() }
    factory { RVAdapterRecipes(null) }
}

val userProfileModule = module {
    viewModel { UserProfileViewModel() }
}

val koinModules = listOf(recipeModule, sessionPreferenceModule, userProfileModule)
