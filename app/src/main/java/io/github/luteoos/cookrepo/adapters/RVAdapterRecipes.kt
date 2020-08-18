package io.github.luteoos.cookrepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.mvvmbaselib.Event
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class RVAdapterRecipes(data: RealmResults<RecipeRealm>?) :
    RealmRecyclerViewAdapter<RecipeRealm, RVAdapterRecipes.RecipesViewHolder>(data, true) {

    private val onClick = MutableLiveData<Event<String>>()

    fun getOnClick(): LiveData<Event<String>> = onClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder =
        RecipesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_recipe, parent, false))

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        data?.get(position)?.let { data ->
            holder.tvName.text = data.name
            holder.tvDesc.text = data.description
            holder.itemView.setOnClickListener {
                onClick.value = Event(data.id)
            }
        }
    }

    inner class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvRecipeTitle)
        val tvDesc = view.findViewById<TextView>(R.id.tvRecipeDesc)
    }
}
