package io.github.luteoos.cookrepo.data.wrapper

import io.github.luteoos.cookrepo.data.view.RecipeViewData

data class RecipeWrapper(
    val isSuccess: Boolean,
    val result: RecipeViewData?
) {
    constructor(recipe: RecipeViewData) : this(true, recipe)
    constructor(isSuccess: Boolean) : this(isSuccess, null)
}
