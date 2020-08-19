package io.github.luteoos.cookrepo.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.github.luteoos.cookrepo.repository.RecipeRepository
import io.github.luteoos.cookrepo.repository.RecipeRepositoryInterface
import io.github.luteoos.cookrepo.utils.Session

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun provideRecipeRepositoryInterface(session: Session): RecipeRepositoryInterface {
        return RecipeRepository(session = session)
    }
}
