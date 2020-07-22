package io.github.luteoos.cookrepo.di

import io.github.luteoos.cookrepo.utils.Session
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
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
    viewModel { MainScreenViewModel() }
}

/**
 * Add your viewModel DI here ex. viewModel { TemplateViewModel() }
 */

val koinModules = listOf(mainScreenModule, sessionPreferenceModule)