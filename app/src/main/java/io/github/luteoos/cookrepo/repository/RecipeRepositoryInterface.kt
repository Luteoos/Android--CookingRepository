package io.github.luteoos.cookrepo.repository

import io.github.luteoos.cookrepo.data.wrapper.RecipesRealmWrapper
import io.reactivex.rxjava3.core.Observable

interface RecipeRepositoryInterface {

    fun getRecipeObservable(): Observable<RecipesRealmWrapper>
    fun getRecipesAll()
    fun getRecipesStarred()
    fun clear()
}
