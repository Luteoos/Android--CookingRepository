package io.github.luteoos.cookrepo.repository

import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.cookrepo.data.wrapper.RecipesRealmWrapper
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.realm.Realm
import timber.log.Timber

class RecipeRepository : RecipeRepositoryInterface {

    private val stream: PublishSubject<RecipesRealmWrapper> = PublishSubject.create()
    private val realm = Realm.getDefaultInstance() // Work around realm not support rxjava3.

    override fun getRecipeObservable() = stream

    override fun getRecipesAll() {
        realm.let {
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
                        stream.onNext(wrapper)
                    },
                    { e ->
                        Timber.e(e)
                        stream.onNext(RecipesRealmWrapper(false))
                    }
                )
        }
    }

    override fun getRecipesStarred() {
    }

    override fun clear() {
        realm.close()
    }
}
