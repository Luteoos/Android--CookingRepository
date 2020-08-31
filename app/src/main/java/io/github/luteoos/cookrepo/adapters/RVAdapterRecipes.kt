package io.github.luteoos.cookrepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.diffutil.RecipesDiffUtilCallback
import io.github.luteoos.cookrepo.data.view.RecipeRecyclerViewData
import io.github.luteoos.cookrepo.utils.Parameters
import io.github.luteoos.mvvmbaselib.Event

class RVAdapterRecipes : RecyclerView.Adapter< RVAdapterRecipes.RecipesViewHolder>() {

    private val data = mutableListOf<RecipeRecyclerViewData>()
    private val onClick = MutableLiveData<Event<Pair<String, String>>>()

    fun getOnClick(): LiveData<Event<Pair<String, String>>> = onClick

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder =
        RecipesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_recipe, parent, false))

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        data[position].let { data ->
            holder.tvName.text = data.name
            holder.tvDesc.text = data.description
            holder.tvDesc.maxLines = 2
            holder.tvName.maxLines = 2
            holder.titleView.setOnClickListener {
                onClick.value = Event(data.id to Parameters.RECIPE_ADAPTER_OPEN)
            }
            holder.btnDelete.setOnClickListener {
                onClick.value = Event(data.id to Parameters.RECIPE_ADAPTER_DELETE)
            }
        }
    }

    fun updateData(new: MutableList<RecipeRecyclerViewData>) {
        val old = data.toMutableList() // .toMutableList needed to copy value not assign by ref
        data.clear()
        data.addAll(new)
        DiffUtil.calculateDiff(RecipesDiffUtilCallback(old, data)).dispatchUpdatesTo(this)
    }

    inner class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvRecipeTitle)
        val tvDesc = view.findViewById<TextView>(R.id.tvRecipeDesc)
        val btnDelete = view.findViewById<ImageButton>(R.id.recipeRemoveButton)
        val titleView = view.findViewById<View>(R.id.title_view)
    }
}
