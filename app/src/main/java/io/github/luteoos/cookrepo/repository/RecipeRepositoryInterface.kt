package io.github.luteoos.cookrepo.repository

import io.github.luteoos.cookrepo.data.wrapper.RecipeWrapper
import io.github.luteoos.cookrepo.data.wrapper.RecipesRealmWrapper
import io.reactivex.rxjava3.core.Observable

interface RecipeRepositoryInterface {

    fun getRecipesObservable(): Observable<RecipesRealmWrapper>
    fun getRecipeObservable(): Observable<RecipeWrapper>
    fun getRecipe(id: String)
    fun getRecipesAll()
    fun getRecipesStarred()
    fun clear()
}
