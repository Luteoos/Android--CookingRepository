package io.github.luteoos.cookrepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.diffutil.RecipeCrumbDiffUtilCallback
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
        val old = data
        data.clear()
        data.addAll(new)
        DiffUtil.calculateDiff(RecipeCrumbDiffUtilCallback(old, data)).dispatchUpdatesTo(this)
    }

    inner class IngredientVH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvIngredientName = view.findViewById<TextInputLayout>(R.id.ingredientNameInput)
        private val tvIngredientAmount = view.findViewById<TextInputLayout>(R.id.ingredientAmountInput)

        fun setIngredientData(data: RecipeCrumb.IngredientAmountViewData) {
            tvIngredientAmount.editText?.setText(data.amount)
            tvIngredientName.editText?.setText(data.ingredient.name)
//            spinnerIngredientMeasure.prompt = data.unit
            // TODO modify to update only 1 value, consider smth diffrent than spinner
            tvIngredientName.editText?.doOnTextChanged { _, _, _, _ ->
                updateData(data)
            }
            tvIngredientAmount.editText?.doOnTextChanged { _, _, _, _ ->
                updateData(data)
            }
        }

        private fun updateData(data: RecipeCrumb.IngredientAmountViewData) {
            data.copy(
                ingredient = data.ingredient.copy(name = tvIngredientName.editText?.text.toString()),
                amount = tvIngredientAmount.editText?.text.toString()
            )
        }
    }

    inner class RecipeStepVH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvStep = view.findViewById<TextInputLayout>(R.id.recipeStepInput)

        fun setStepData(data: RecipeCrumb.RecipeStepViewData) {
            tvStep.editText?.setText(data.text)
            tvStep.editText?.doOnTextChanged { text, _, _, _ ->
                itemUpdate.onNext(data.copy(text = text.toString()))
            }
        }
    }
}
