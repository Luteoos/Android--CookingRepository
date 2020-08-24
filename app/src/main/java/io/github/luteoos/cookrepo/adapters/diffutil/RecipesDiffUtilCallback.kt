package io.github.luteoos.cookrepo.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import io.github.luteoos.cookrepo.data.view.RecipeRecyclerViewData

class RecipesDiffUtilCallback(private val old: MutableList<RecipeRecyclerViewData>, private val new: MutableList<RecipeRecyclerViewData>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition].id == new[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == new[newItemPosition]
}
