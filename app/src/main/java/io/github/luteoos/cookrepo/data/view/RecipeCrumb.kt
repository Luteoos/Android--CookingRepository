package io.github.luteoos.cookrepo.data.view

sealed class RecipeCrumb {
    data class IngredientAmountViewData(
        val id: String,
        val ingredient: IngredientViewData,
        val amount: Int,
        val unit: String
    ) : RecipeCrumb()
    data class RecipeStepViewData(
        val id: String,
        val text: String
    ) : RecipeCrumb()
}
