package io.github.luteoos.cookrepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
            Parameters.TYPE_HEADER ->
                HeaderVH(LayoutInflater.from(parent.context).inflate(R.layout.rv_header_recipe_crumb, parent, false))
            else -> throw Exception(" No defined view type")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data[position].let {
            when (it) {
                is RecipeCrumb.IngredientAmountViewData -> (holder as IngredientVH).setIngredientData(it)
                is RecipeCrumb.RecipeStepViewData -> (holder as RecipeStepVH).setStepData(it)
                is RecipeCrumb.RecyclerViewHeader -> (holder as HeaderVH).setHeaderData(it)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is RecipeCrumb.IngredientAmountViewData -> Parameters.TYPE_INGREDIENT
            is RecipeCrumb.RecipeStepViewData -> Parameters.TYPE_STEP
            is RecipeCrumb.RecyclerViewHeader -> Parameters.TYPE_HEADER
        }
    }

    fun updateData(new: MutableList<RecipeCrumb>) {
        // TODO add DiffUtil here
        val old = data
        data.clear()
        data.addAll(new)
        DiffUtil.calculateDiff(RecipeCrumbDiffUtilCallback(old, data)).dispatchUpdatesTo(this)
    }

    // TODO fdinish it, add button visibility and correct texts, maybe color background
    inner class HeaderVH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvHeaderTitle = view.findViewById<TextView>(R.id.tvRecipeHeader)
        private val btnHeader = view.findViewById<Button>(R.id.btnRecipeHeader)

        fun setHeaderData(data: RecipeCrumb.RecyclerViewHeader) {
            when (data.type) {
                Parameters.HEADER_TYPE_STEP -> {
                    tvHeaderTitle.text = itemView.context.getString(R.string.profile)
                }
                Parameters.HEADER_TYPE_INGREDIENT -> {
                    tvHeaderTitle.text = "INGREDIENTS"
                }
            }
        }
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
