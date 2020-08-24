package io.github.luteoos.cookrepo.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.github.luteoos.cookrepo.dao.RecipeDao
import io.github.luteoos.cookrepo.repository.RecipeRepository
import io.github.luteoos.cookrepo.repository.RecipeRepositoryInterface
import io.github.luteoos.cookrepo.utils.Session

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun provideRecipeDao() = RecipeDao()

    @Provides
    fun provideRecipeRepositoryInterface(session: Session, recipeDao: RecipeDao): RecipeRepositoryInterface {
        return RecipeRepository(session = session, recipeDao = recipeDao)
    }
}
