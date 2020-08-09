package io.github.luteoos.cookrepo.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import io.github.luteoos.cookrepo.data.view.RecipeCrumb

class RecipeCrumbDiffUtilCallback(private val old: MutableList<RecipeCrumb>, private val new: MutableList<RecipeCrumb>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].let { old ->
            new[newItemPosition].let { new ->
                when (old) {
                    is RecipeCrumb.RecipeStepViewData -> when (new) {
                        is RecipeCrumb.RecipeStepViewData -> new.id == old.id
                        is RecipeCrumb.IngredientAmountViewData -> false
                    }
                    is RecipeCrumb.IngredientAmountViewData -> when (new) {
                        is RecipeCrumb.IngredientAmountViewData -> new.id == old.id
                        is RecipeCrumb.RecipeStepViewData -> false
                    }
                }
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].let { old ->
            new[newItemPosition].let { new ->
                when (old) {
                    is RecipeCrumb.RecipeStepViewData -> when (new) {
                        is RecipeCrumb.RecipeStepViewData -> new == old
                        is RecipeCrumb.IngredientAmountViewData -> false
                    }
                    is RecipeCrumb.IngredientAmountViewData -> when (new) {
                        is RecipeCrumb.IngredientAmountViewData -> new == old
                        is RecipeCrumb.RecipeStepViewData -> false
                    }
                }
            }
        }
    }
}
