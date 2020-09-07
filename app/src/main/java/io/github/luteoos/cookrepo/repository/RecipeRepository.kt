package io.github.luteoos.cookrepo.repository

import io.github.luteoos.cookrepo.dao.RecipeDao
import io.github.luteoos.cookrepo.data.realm.IngredientAmountRealm
import io.github.luteoos.cookrepo.data.realm.RecipeStepRealm
import io.github.luteoos.cookrepo.data.repo.RecipeRepoData
import io.github.luteoos.cookrepo.data.view.IngredientViewData
import io.github.luteoos.cookrepo.data.view.RecipeCrumb
import io.github.luteoos.cookrepo.data.view.RecipeRecyclerViewData
import io.github.luteoos.cookrepo.data.wrapper.RecipeWrapper
import io.github.luteoos.cookrepo.data.wrapper.RecipesListWrapper
import io.github.luteoos.cookrepo.utils.Session
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import io.realm.RealmList
import timber.log.Timber

class RecipeRepository(
    private val session: Session,
    private val recipeDao: RecipeDao
) : RecipeRepositoryInterface {

    private val streamRecipes: PublishSubject<RecipesListWrapper> = PublishSubject.create()
    private val streamRecipe: PublishSubject<RecipeWrapper> = PublishSubject.create()
    private val recipeStream = Single.fromCallable {
        recipeDao.getRecipes()
    }

    override fun getRecipesObservable(): Observable<RecipesListWrapper> = streamRecipes

    override fun getRecipeObservable(): Observable<RecipeWrapper> = streamRecipe

    override fun getRecipe(id: String) {
        Single
            .fromCallable {
                recipeDao.getRecipe(id)
            }
            .map { result ->
                if (result == null)
                    throw Exception("No recipe found")
                else
                    RecipeRepoData(
                        result.id,
                        result.name,
                        result.description,
                        result.starred,
                        mapIngredientToRecipeCrumb(result.ingredients),
                        mapStepToRecipeCrumb(result.steps)
                    )
            }
            .subscribe(
                { wrapper ->
                    streamRecipe.onNext(RecipeWrapper(wrapper))
                },
                { e ->
                    Timber.e(e)
                    streamRecipe.onNext(RecipeWrapper(false))
                }
            )
    }

    override fun getRecipesAll() {
        recipeStream
            .map { result ->
                RecipesListWrapper(
                    result.map { RecipeRecyclerViewData(it.id, it.name, it.description) }.toMutableList()
                ) // potential problem with not preserving order
            }
            .subscribe(
                { wrapper ->
                    streamRecipes.onNext(wrapper)
                },
                { e ->
                    Timber.e(e)
                    streamRecipes.onNext(RecipesListWrapper(false))
                }
            )
    }

    override fun getRecipesStarred() {
        recipeStream
            .map {
                it.filter { recipe -> recipe.starred }
            }
            .map { result ->
                RecipesListWrapper(
                    result.map { RecipeRecyclerViewData(it.id, it.name, it.description) }.toMutableList()
                ) // potential problem with not preserving order
            }
            .subscribe(
                { wrapper ->
                    streamRecipes.onNext(wrapper)
                },
                { e ->
                    Timber.e(e)
                    streamRecipes.onNext(RecipesListWrapper(false))
                }
            )
    }

    override fun createRecipe(): String {
        return recipeDao.createRecipe(session.username)
    }

    override fun createIngredientAmount(id: String) {
        recipeDao.createIngredientAmount(id, session.username)
        getRecipe(id)
    }

    override fun createStep(id: String) {
        recipeDao.createStep(id, session.username)
        getRecipe(id)
    }

    override fun updateRecipeTitle(id: String, title: String) {
        recipeDao.updateRecipeTitle(id, title)
        getRecipe(id)
    }

    override fun updateRecipeDesc(id: String, description: String) {
        recipeDao.updateRecipeDesc(id, description)
        getRecipe(id)
    }

    override fun updateRecipeStarred(id: String, starred: Boolean) {
        recipeDao.updateRecipeStarred(id, starred)
        getRecipe(id)
    }

    override fun updateIngredientAmount(data: RecipeCrumb.IngredientAmountViewData) {
        recipeDao.updateIngredientAmount(data.id, data.amount, data.ingredient.name, data.carted)
    }

    override fun updateStep(data: RecipeCrumb.RecipeStepViewData) {
        recipeDao.updateStep(data.id, data.text)
    }

    override fun deleteRecipe(id: String) {
        recipeDao.deleteRecipe(id)
    }

    override fun deleteIngredientAmount(id: String, recipeId: String) {
        recipeDao.deleteIngredientAmount(id, recipeId)
        getRecipe(recipeId)
    }

    override fun deleteStep(id: String, recipeId: String) {
        recipeDao.deleteStep(id, recipeId)
        getRecipe(recipeId)
    }

    private fun mapIngredientToRecipeCrumb(ingredients: RealmList<IngredientAmountRealm>): MutableList<RecipeCrumb> {
        return mutableListOf<RecipeCrumb>().also { list ->
            list.addAll(
                ingredients.map { amountRealm ->
                    amountRealm.ingredient?.let { ingredientRealm ->
                        RecipeCrumb.IngredientAmountViewData(
                            amountRealm.id,
                            IngredientViewData(ingredientRealm.id, ingredientRealm.name ?: ""),
                            amountRealm.amount,
                            amountRealm.isInCart
                        )
                    } ?: throw Exception("No ingredient data")
                }
            )
        }
    }

    private fun mapStepToRecipeCrumb(steps: RealmList<RecipeStepRealm>): MutableList<RecipeCrumb> {
        return mutableListOf<RecipeCrumb>().also { list ->
            list.addAll(
                steps.map {
                    RecipeCrumb.RecipeStepViewData(it.id, it.text)
                }
            )
        }
    }
}
