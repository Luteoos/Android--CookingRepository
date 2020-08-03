package io.github.luteoos.cookrepo.data.wrapper

import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.realm.RealmResults

data class RecipesRealmWrapper(
    val isSuccess: Boolean,
    val result: RealmResults<RecipeRealm>?
) {
    constructor(list: RealmResults<RecipeRealm>) : this(true, list)
    constructor(isSuccess: Boolean) : this(isSuccess, null)
}
