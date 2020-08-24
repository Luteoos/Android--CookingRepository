package io.github.luteoos.cookrepo.data.wrapper

import io.github.luteoos.cookrepo.data.view.RecipeRecyclerViewData

data class RecipesListWrapper(
    val isSuccess: Boolean,
    val result: MutableList<RecipeRecyclerViewData>?
) {
    constructor(list: MutableList<RecipeRecyclerViewData>) : this(true, list)
    constructor(isSuccess: Boolean) : this(isSuccess, null)
}
