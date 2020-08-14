package io.github.luteoos.cookrepo.repository

import io.github.luteoos.cookrepo.data.realm.IngredientAmountRealm
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.cookrepo.data.realm.RecipeStepRealm
import io.github.luteoos.cookrepo.data.view.IngredientViewData
import io.github.luteoos.cookrepo.data.view.RecipeCrumb
import io.github.luteoos.cookrepo.data.view.RecipeViewData
import io.github.luteoos.cookrepo.data.wrapper.RecipeWrapper
import io.github.luteoos.cookrepo.data.wrapper.RecipesRealmWrapper
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.realm.Realm
import io.realm.RealmList
import timber.log.Timber

class RecipeRepository : RecipeRepositoryInterface {

    private val streamRecipes: PublishSubject<RecipesRealmWrapper> = PublishSubject.create()
    private val streamRecipe: PublishSubject<RecipeWrapper> = PublishSubject.create()
    private var realm: Realm? = null // Work around realm not support rxjava3.

    override fun getRecipesObservable(): Observable<RecipesRealmWrapper> = streamRecipes

    override fun getRecipeObservable(): Observable<RecipeWrapper> = streamRecipe

    override fun getRecipe(id: String) {
        getRealm().let {
            Flowable
                .just(
                    it.where(RecipeRealm::class.java)
                        .equalTo("id", id)
                        .findFirst()
                )
                .map { result ->
                    if (result == null)
                        throw Exception("No recipe found")
                    else
                        it.copyFromRealm(result).let { recipeRealm ->
                            RecipeViewData(
                                result.id,
                                recipeRealm.name,
                                recipeRealm.description,
                                recipeRealm.starred,
                                mapToRecipeCrumb(recipeRealm.ingredients, recipeRealm.steps)
                            )
                        }
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
    }

    override fun getRecipesAll() {
        getRealm().let {
            Flowable
                .just(
                    it.where(RecipeRealm::class.java) // Work around bc Realm doesnt support RxJava3 yet.
                        .findAll()
                )
                .map { result ->
                    RecipesRealmWrapper(result)
                }
                .subscribe(
                    { wrapper ->
                        streamRecipes.onNext(wrapper)
                    },
                    { e ->
                        Timber.e(e)
                        streamRecipes.onNext(RecipesRealmWrapper(false))
                    }
                )
        }
    }

    override fun getRecipesStarred() {
        getRealm().let {
            Flowable
                .just(
                    it.where(RecipeRealm::class.java) // Work around bc Realm doesnt support RxJava3 yet.
                        .equalTo("starred", true)
                        .findAll()
                )
                .map { result ->
                    RecipesRealmWrapper(result)
                }
                .subscribe(
                    { wrapper ->
                        streamRecipes.onNext(wrapper)
                    },
                    { e ->
                        Timber.e(e)
                        streamRecipes.onNext(RecipesRealmWrapper(false))
                    }
                )
        }
    }

    override fun clear() {
        getRealm().close()
    }

    private fun getRealm(): Realm {
        realm?.let {
            return it
        } ?: run {
            realm = Realm.getDefaultInstance()
            return realm!!
        }
    }

    private fun mapToRecipeCrumb(ingredients: RealmList<IngredientAmountRealm>, steps: RealmList<RecipeStepRealm>): MutableList<RecipeCrumb> {
        return mutableListOf<RecipeCrumb>().also { list ->
            list.addAll(
                ingredients.map { amountRealm ->
                    amountRealm.ingredient?.let { ingredientRealm ->
                        RecipeCrumb.IngredientAmountViewData(
                            amountRealm.id,
                            IngredientViewData(ingredientRealm.id, ingredientRealm.name ?: ""),
                            amountRealm.amount
                        )
                    } ?: throw Exception("No ingredient data")
                }
            )
            list.addAll(
                steps.map {
                    RecipeCrumb.RecipeStepViewData(it.id, it.text)
                }
            )
        }
    }
}
