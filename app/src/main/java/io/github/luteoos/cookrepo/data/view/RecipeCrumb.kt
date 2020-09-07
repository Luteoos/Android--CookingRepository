package io.github.luteoos.cookrepo.data.view

sealed class RecipeCrumb {
    data class IngredientAmountViewData(
        val id: String,
        val ingredient: IngredientViewData,
        val amount: String,
        val carted: Boolean
    ) : RecipeCrumb()
    data class RecipeStepViewData(
        val id: String,
        val text: String
    ) : RecipeCrumb()
    data class RecyclerViewHeader(
        val type: String,
        val extra: String = ""
    ) : RecipeCrumb()
}