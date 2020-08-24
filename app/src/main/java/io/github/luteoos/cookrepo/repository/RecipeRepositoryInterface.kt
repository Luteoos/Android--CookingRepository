package io.github.luteoos.cookrepo.repository

import io.github.luteoos.cookrepo.data.view.RecipeCrumb
import io.github.luteoos.cookrepo.data.wrapper.RecipeWrapper
import io.github.luteoos.cookrepo.data.wrapper.RecipesListWrapper
import io.reactivex.rxjava3.core.Observable

interface RecipeRepositoryInterface {

    fun getRecipesObservable(): Observable<RecipesListWrapper>
    fun getRecipeObservable(): Observable<RecipeWrapper>
    fun getRecipe(id: String)
    fun getRecipesAll()
    fun getRecipesStarred()
    fun createRecipe(): String
    fun createIngredientAmount(id: String)
    fun createStep(id: String)
    fun updateRecipeTitle(id: String, title: String)
    fun updateRecipeDesc(id: String, description: String)
    fun updateRecipeStarred(id: String, starred: Boolean)
    fun updateIngredientAmount(data: RecipeCrumb.IngredientAmountViewData)
    fun updateStep(data: RecipeCrumb.RecipeStepViewData)
    fun deleteRecipe(id: String)
    fun deleteIngredientAmount(id: String, recipeId: String)
    fun deleteStep(id: String, recipeId: String)
}
