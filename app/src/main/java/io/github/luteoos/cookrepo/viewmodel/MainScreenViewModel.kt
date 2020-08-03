package io.github.luteoos.cookrepo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.luteoos.cookrepo.baseAbstract.BaseViewModel
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.cookrepo.repository.RecipeRepositoryInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.realm.RealmResults

class MainScreenViewModel(private val recipeRepo: RecipeRepositoryInterface) : BaseViewModel() {

    private val subscribers = CompositeDisposable()
    private val recipes = MutableLiveData<RealmResults<RecipeRealm>>()

    init {
        recipeRepo.getRecipeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isSuccess)
                    recipes.value = it.result
            }
    }

    fun getRecipesLiveData(): LiveData<RealmResults<RecipeRealm>> = recipes

    fun getRecipesAll() {
        recipeRepo.getRecipesAll()
    }

    override fun onCleared() {
        subscribers.dispose()
        recipeRepo.clear()
        super.onCleared()
    }
}
