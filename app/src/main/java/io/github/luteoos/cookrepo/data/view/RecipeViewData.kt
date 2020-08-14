package io.github.luteoos.cookrepo.data.view

data class RecipeViewData(
    val id: String,
    var name: String,
    var description: String,
    var starred: Boolean,
    var crumbList: MutableList<RecipeCrumb>
)
