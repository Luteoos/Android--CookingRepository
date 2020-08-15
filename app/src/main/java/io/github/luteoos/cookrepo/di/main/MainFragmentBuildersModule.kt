package io.github.luteoos.cookrepo.di.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.luteoos.cookrepo.view.fragment.RecipeEditFragment
import io.github.luteoos.cookrepo.view.fragment.RecipeFragment
import io.github.luteoos.cookrepo.view.fragment.RecipeListFragment
import io.github.luteoos.cookrepo.view.fragment.StarredListFragment
import io.github.luteoos.cookrepo.view.fragment.UserProfileFragment

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeRecipeEditFragment(): RecipeEditFragment

    @ContributesAndroidInjector
    abstract fun contributeRecipeFragment(): RecipeFragment

    @ContributesAndroidInjector
    abstract fun contributeRecipeListFragment(): RecipeListFragment

    @ContributesAndroidInjector
    abstract fun contributeStarredListFragment(): StarredListFragment

    @ContributesAndroidInjector
    abstract fun contributeUserProfileFragment(): UserProfileFragment
}
