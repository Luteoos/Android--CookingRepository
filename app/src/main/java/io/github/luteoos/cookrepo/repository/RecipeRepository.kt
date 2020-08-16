package io.github.luteoos.cookrepo.repository

import io.github.luteoos.cookrepo.data.realm.IngredientAmountRealm
import io.github.luteoos.cookrepo.data.realm.IngredientRealm
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.cookrepo.data.realm.RecipeStepRealm
import io.github.luteoos.cookrepo.data.repo.RecipeRepoData
import io.github.luteoos.cookrepo.data.view.IngredientViewData
import io.github.luteoos.cookrepo.data.view.RecipeCrumb
import io.github.luteoos.cookrepo.data.wrapper.RecipeWrapper
import io.github.luteoos.cookrepo.data.wrapper.RecipesRealmWrapper
import io.github.luteoos.cookrepo.utils.Session
import io.github.luteoos.cookrepo.utils.getFirst
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.realm.Realm
import io.realm.RealmList
import timber.log.Timber

class RecipeRepository(private val session: Session) : RecipeRepositoryInterface {

    private val streamRecipes: PublishSubject<RecipesRealmWrapper> = PublishSubject.create()
    private val streamRecipe: PublishSubject<RecipeWrapper> = PublishSubject.create()
    private var realm: Realm? = null // Work around realm not support rxjava3.

    override fun getRecipesObservable(): Observable<RecipesRealmWrapper> = streamRecipes

    override fun getRecipeObservable(): Observable<RecipeWrapper> = streamRecipe

    override fun getRecipe(id: String) {
        getRealm().let {
            Flowable
                .just(
                    it.where(RecipeRealm::class.java) // Work around bc Realm doesnt support RxJava3 yet.
                        .equalTo("id", id)
                        .findFirst()
                )
                .map { result ->
                    if (result == null)
                        throw Exception("No recipe found")
                    else
                        it.copyFromRealm(result).let { recipeRealm ->
                            RecipeRepoData(
                                result.id,
                                recipeRealm.name,
                                recipeRealm.description,
                                recipeRealm.starred,
                                mapIngredientToRecipeCrumb(recipeRealm.ingredients),
                                mapStepToRecipeCrumb(recipeRealm.steps)
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

    override fun createRecipe(): String {
        val recipe = RecipeRealm().create(author = session.username)
        getRealm().executeTransaction {
            it.copyToRealm(recipe)
        }
        return recipe.id
    }

    override fun createIngredientAmount(id: String) {
        getRealm().executeTransaction {
            it.getFirst(id, RecipeRealm::class.java)?.ingredients?.add(
                IngredientAmountRealm().create(
                    IngredientRealm().create(author = session.username),
                    author = session.username
                )
            )
        }
        getRecipe(id)
    }

    override fun createStep(id: String) {
        getRealm().executeTransaction {
            it.getFirst(id, RecipeRealm::class.java)?.steps?.add(
                RecipeStepRealm().create(author = session.username)
            )
        }
        getRecipe(id)
    }

    override fun updateIngredientAmount(data: RecipeCrumb.IngredientAmountViewData) {
        getRealm().executeTransaction {
            it.getFirst(data.id, IngredientAmountRealm::class.java)?.let { amount ->
                amount.amount = data.amount
                amount.ingredient?.name = data.ingredient.name
            }
        }
    }

    override fun updateStep(data: RecipeCrumb.RecipeStepViewData) {
        getRealm().executeTransaction {
            it.getFirst(data.id, RecipeStepRealm::class.java)?.let { step ->
                step.text = data.text
            }
        }
    }

    override fun deleteIngredientAmount(id: String, recipeId: String) {
        getRealm().executeTransaction {
            it.getFirst(id, IngredientAmountRealm::class.java)?.cascadeDelete()
        }
        getRecipe(recipeId)
    }

    override fun deleteStep(id: String, recipeId: String) {
        getRealm().executeTransaction {
            it.getFirst(id, RecipeStepRealm::class.java)?.deleteFromRealm()
        }
        getRecipe(recipeId)
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

    private fun mapIngredientToRecipeCrumb(ingredients: RealmList<IngredientAmountRealm>): MutableList<RecipeCrumb> {
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
