package io.github.luteoos.cookrepo.data.wrapper

import io.github.luteoos.cookrepo.data.repo.RecipeRepoData

data class RecipeWrapper(
    val isSuccess: Boolean,
    val result: RecipeRepoData?
) {
    constructor(recipe: RecipeRepoData) : this(true, recipe)
    constructor(isSuccess: Boolean) : this(isSuccess, null)
}
