package io.github.luteoos.cookrepo.di.main

import dagger.Module
import dagger.Provides
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipeCrumbs
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipes
import io.github.luteoos.cookrepo.repository.RecipeRepository
import io.github.luteoos.cookrepo.repository.RecipeRepositoryInterface
import io.github.luteoos.cookrepo.utils.Session

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideRecipesAdapter(): RVAdapterRecipes {
        return RVAdapterRecipes(null)
    }

    @MainScope
    @Provides
    fun provideRecipesCrumbAdapter(): RVAdapterRecipeCrumbs {
        return RVAdapterRecipeCrumbs()
    }

    @MainScope
    @Provides
    fun provideRecipeRepositoryInterface(session: Session): RecipeRepositoryInterface {
        return RecipeRepository(session = session)
    }
}
