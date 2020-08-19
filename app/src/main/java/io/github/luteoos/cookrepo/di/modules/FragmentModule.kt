package io.github.luteoos.cookrepo.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipeCrumbs
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipes

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    fun provideRecipesAdapter(): RVAdapterRecipes {
        return RVAdapterRecipes(null)
    }

    @Provides
    fun provideRecipesCrumbAdapter(): RVAdapterRecipeCrumbs {
        return RVAdapterRecipeCrumbs()
    }
}
