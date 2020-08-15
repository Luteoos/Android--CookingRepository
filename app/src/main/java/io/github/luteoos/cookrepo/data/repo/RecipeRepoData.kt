package io.github.luteoos.cookrepo.data.repo

import io.github.luteoos.cookrepo.data.view.RecipeCrumb

data class RecipeRepoData(
    val id: String,
    var name: String,
    var description: String,
    var starred: Boolean,
    var ingredientCrumbList: MutableList<RecipeCrumb>,
    var stepCrumbList: MutableList<RecipeCrumb>
)
