package io.github.luteoos.cookrepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.data.view.RecipeCrumb
import io.github.luteoos.cookrepo.utils.Parameters
import io.reactivex.rxjava3.subjects.PublishSubject

class RVAdapterRecipeCrumbs : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<RecipeCrumb>()
    val itemUpdate: PublishSubject<RecipeCrumb> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Parameters.TYPE_STEP ->
                RecipeStepVH(LayoutInflater.from(parent.context).inflate(R.layout.rv_step, parent, false))
            Parameters.TYPE_INGREDIENT ->
                IngredientVH(LayoutInflater.from(parent.context).inflate(R.layout.rv_ingredient, parent, false))
            else -> throw Exception(" No defined view type")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data[position].let {
            when (it) {
                is RecipeCrumb.IngredientAmountViewData -> (holder as IngredientVH).setIngredientData(it)
                is RecipeCrumb.RecipeStepViewData -> (holder as RecipeStepVH).setStepData(it)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is RecipeCrumb.IngredientAmountViewData -> Parameters.TYPE_INGREDIENT
            is RecipeCrumb.RecipeStepViewData -> Parameters.TYPE_STEP
        }
    }

    fun updateData(new: MutableList<RecipeCrumb>) {
        // TODO add DiffUtil here
        data.clear()
        data.addAll(new)
        notifyDataSetChanged()
    }

    inner class IngredientVH(view: View) : RecyclerView.ViewHolder(view) {

        fun setIngredientData(data: RecipeCrumb.IngredientAmountViewData) {
        }
    }

    inner class RecipeStepVH(view: View) : RecyclerView.ViewHolder(view) {
//        val tvText = view.findViewById<TextView>()
        fun setStepData(data: RecipeCrumb.RecipeStepViewData) {
        }
    }
}
