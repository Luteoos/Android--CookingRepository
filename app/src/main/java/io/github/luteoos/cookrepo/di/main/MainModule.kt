package io.github.luteoos.cookrepo.di.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipeCrumbs
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipes
import io.github.luteoos.cookrepo.repository.RecipeRepository
import io.github.luteoos.cookrepo.repository.RecipeRepositoryInterface
import io.github.luteoos.cookrepo.utils.Session

@Module
@InstallIn(ActivityComponent::class)
class MainModule {

    @Provides
    fun provideRecipesAdapter(): RVAdapterRecipes {
        return RVAdapterRecipes(null)
    }

    @Provides
    fun provideRecipesCrumbAdapter(): RVAdapterRecipeCrumbs {
        return RVAdapterRecipeCrumbs()
    }

    @Provides
    fun provideRecipeRepositoryInterface(session: Session): RecipeRepositoryInterface {
        return RecipeRepository(session = session)
    }
}
