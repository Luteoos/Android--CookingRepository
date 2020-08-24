package io.github.luteoos.cookrepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.diffutil.RecipeCrumbDiffUtilCallback
import io.github.luteoos.cookrepo.data.view.RecipeCrumb
import io.github.luteoos.cookrepo.utils.Parameters
import io.github.luteoos.cookrepo.utils.setTextChanged
import io.github.luteoos.mvvmbaselib.Event

class RVAdapterRecipeCrumbs : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var editable = false
    private val data = mutableListOf<RecipeCrumb>()
    private val itemUpdate = MutableLiveData<Event<Pair<RecipeCrumb, String>>>()

    // todo modify layouts to icons on right always/add swipable layout
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

    fun getItemUpdate(): LiveData<Event<Pair<RecipeCrumb, String>>> = itemUpdate

    fun setEditable(editable: Boolean) {
        this.editable = editable
    }

    fun updateData(new: MutableList<RecipeCrumb>) {
        val old = data.toMutableList() // .toMutableList needed to copy value not assign by ref
        data.clear()
        data.addAll(new)
        DiffUtil.calculateDiff(RecipeCrumbDiffUtilCallback(old, data)).dispatchUpdatesTo(this)
    }

    inner class HeaderVH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvHeaderTitle = view.findViewById<TextView>(R.id.tvRecipeHeader)
        private val btnHeader = view.findViewById<Button>(R.id.btnRecipeHeader)

        fun setHeaderData(data: RecipeCrumb.RecyclerViewHeader) {
            btnHeader.visibility = if (editable) View.VISIBLE else View.GONE
            btnHeader.setOnClickListener {
                itemUpdate.value = Event(data.copy() to Parameters.CRUMB_EXTRA_ADD)
            }
            tvHeaderTitle.text = when (data.type) {
                Parameters.HEADER_TYPE_STEP -> itemView.context.getString(R.string.steps_header)
                Parameters.HEADER_TYPE_INGREDIENT -> itemView.context.getString(R.string.ingredients_header)
                else -> throw Exception("No recyclerViewHeader type found")
            }
        }
    }

    inner class IngredientVH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvIngredientName = view.findViewById<TextInputLayout>(R.id.ingredientNameInput)
        private val tvIngredientAmount = view.findViewById<TextInputLayout>(R.id.ingredientAmountInput)
        private val btnIngredientRemove = view.findViewById<ImageButton>(R.id.ingredientRemoveButton)
        private val btnCart = view.findViewById<ImageButton>(R.id.recipeCartButton)

        fun setIngredientData(data: RecipeCrumb.IngredientAmountViewData) {
            btnCart.setImageResource(
                if (data.carted)
                    R.drawable.ic_remove_shopping_cart_24px
                else
                    R.drawable.ic_add_shopping_cart_24px
            )
            btnCart.visibility = if (editable) View.GONE else View.VISIBLE
            btnIngredientRemove.visibility = if (editable) View.VISIBLE else View.GONE
            tvIngredientAmount.isEnabled = editable
            tvIngredientName.isEnabled = editable
            tvIngredientAmount.editText?.setTextChanged(data.amount)
            tvIngredientName.editText?.setTextChanged(data.ingredient.name)

            btnCart.setOnClickListener {
                itemUpdate.value = Event(updateData(data, !data.carted) to Parameters.CRUMB_EXTRA_EDIT)
            }
            btnIngredientRemove.setOnClickListener {
                itemUpdate.value = Event(data.copy() to Parameters.CRUMB_EXTRA_DELETE)
            }
            tvIngredientName.editText?.doOnTextChanged { _, _, _, _ ->
                itemUpdate.value = Event(updateData(data) to Parameters.CRUMB_EXTRA_EDIT)
            }
            tvIngredientAmount.editText?.doOnTextChanged { _, _, _, _ ->
                itemUpdate.value = Event(updateData(data) to Parameters.CRUMB_EXTRA_EDIT)
            }
        }

        private fun updateData(data: RecipeCrumb.IngredientAmountViewData, isCarted: Boolean? = null) =
            data.copy(
                ingredient = data.ingredient.copy(name = tvIngredientName.editText?.text.toString()),
                amount = tvIngredientAmount.editText?.text.toString(),
                carted = isCarted ?: data.carted
            )
    }

    inner class RecipeStepVH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvStep = view.findViewById<TextInputLayout>(R.id.recipeStepInput)
        private val btnRecipeRemove = view.findViewById<ImageButton>(R.id.recipeRemoveButton)

        fun setStepData(data: RecipeCrumb.RecipeStepViewData) {
            btnRecipeRemove.visibility = if (editable) View.VISIBLE else View.GONE
            tvStep.isEnabled = editable
            tvStep.editText?.setTextChanged(data.text)

            btnRecipeRemove.setOnClickListener {
                itemUpdate.value = Event(data.copy() to Parameters.CRUMB_EXTRA_DELETE)
            }
            tvStep.editText?.doOnTextChanged { text, _, _, _ ->
                itemUpdate.value = Event(data.copy(text = text.toString()) to Parameters.CRUMB_EXTRA_EDIT)
            }
        }
    }
}
