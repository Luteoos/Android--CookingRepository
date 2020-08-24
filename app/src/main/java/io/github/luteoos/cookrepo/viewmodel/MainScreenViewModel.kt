package io.github.luteoos.cookrepo.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.luteoos.cookrepo.baseAbstract.BaseViewModel
import io.github.luteoos.cookrepo.data.repo.RecipeRepoData
import io.github.luteoos.cookrepo.data.view.LoadingState
import io.github.luteoos.cookrepo.data.view.RecipeCrumb
import io.github.luteoos.cookrepo.data.view.RecipeRecyclerViewData
import io.github.luteoos.cookrepo.data.view.RecipeViewData
import io.github.luteoos.cookrepo.repository.RecipeRepositoryInterface
import io.github.luteoos.cookrepo.utils.Parameters
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class MainScreenViewModel
@ViewModelInject
constructor(private val recipeRepo: RecipeRepositoryInterface) : BaseViewModel() {

    private val subscribers = CompositeDisposable()
    private val recipes = MutableLiveData<MutableList<RecipeRecyclerViewData>>()
    private val recipe = MutableLiveData<RecipeViewData>()
    private val loadingState = MutableLiveData<LoadingState>()

    init {
        Timber.d(" ${this.javaClass.canonicalName} View model created  hashCode ${this.hashCode()}")
        subscribers.addAll(
            recipeRepo.getRecipesObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isSuccess)
                        recipes.value = it.result
                },
            recipeRepo.getRecipeObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isSuccess)
                        recipe.value = mapRecipeToViewData(it.result!!)
                }
        )
    }

    fun getLoadingState(): LiveData<LoadingState> = loadingState

    fun startLoading() {
        loadingState.value = LoadingState.StartLoading
    }

    fun stopLoading() {
        loadingState.value = LoadingState.StopLoading
    }

    fun getRecipesLiveData(): LiveData<MutableList<RecipeRecyclerViewData>> = recipes

    fun getRecipeLiveData(): LiveData<RecipeViewData> = recipe

    fun getRecipe(id: String) {
        recipeRepo.getRecipe(id)
    }

    fun getRecipesAll() {
        recipeRepo.getRecipesAll()
    }

    fun getRecipesStarred() {
        recipeRepo.getRecipesStarred()
    }

    fun createRecipe() =
        recipeRepo.createRecipe()

    fun updateRecipe(id: String, title: String? = null, description: String? = null, starred: Boolean? = null) {
        title?.let {
            recipeRepo.updateRecipeTitle(id, it)
        }
        description?.let {
            recipeRepo.updateRecipeDesc(id, it)
        }
        starred?.let {
            recipeRepo.updateRecipeStarred(id, it)
        }
    }

    fun updateRecipe(id: String, crumb: RecipeCrumb, extra: String) {
        when (crumb) {
            is RecipeCrumb.IngredientAmountViewData -> when (extra) {
                Parameters.CRUMB_EXTRA_EDIT -> recipeRepo.updateIngredientAmount(crumb)
                Parameters.CRUMB_EXTRA_DELETE -> recipeRepo.deleteIngredientAmount(crumb.id, id)
            }
            is RecipeCrumb.RecipeStepViewData -> when (extra) {
                Parameters.CRUMB_EXTRA_EDIT -> recipeRepo.updateStep(crumb)
                Parameters.CRUMB_EXTRA_DELETE -> recipeRepo.deleteStep(crumb.id, id)
            }
            is RecipeCrumb.RecyclerViewHeader -> when (crumb.type) {
                Parameters.HEADER_TYPE_STEP -> when (extra) {
                    Parameters.CRUMB_EXTRA_ADD -> recipeRepo.createStep(id)
                }
                Parameters.HEADER_TYPE_INGREDIENT -> when (extra) {
                    Parameters.CRUMB_EXTRA_ADD -> recipeRepo.createIngredientAmount(id)
                }
            }
        }
    }

    override fun onCleared() {
        subscribers.dispose()
        super.onCleared()
    }

    private fun mapRecipeToViewData(data: RecipeRepoData): RecipeViewData {
        return RecipeViewData(
            data.id,
            data.name,
            data.description,
            data.starred,
            mutableListOf<RecipeCrumb>().apply {
                add(RecipeCrumb.RecyclerViewHeader(Parameters.HEADER_TYPE_INGREDIENT))
                addAll(data.ingredientCrumbList)
                add(RecipeCrumb.RecyclerViewHeader(Parameters.HEADER_TYPE_STEP))
                addAll(data.stepCrumbList)
            }
        )
    }
}
