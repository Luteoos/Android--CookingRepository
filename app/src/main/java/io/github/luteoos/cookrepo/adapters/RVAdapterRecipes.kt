package io.github.luteoos.cookrepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.reactivex.rxjava3.subjects.PublishSubject
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class RVAdapterRecipes(data: RealmResults<RecipeRealm>?) :
    RealmRecyclerViewAdapter<RecipeRealm, RVAdapterRecipes.RecipesViewHolder>(data, true) {

    val onClick : PublishSubject<String> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder =
        RecipesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_recipe, parent, false))

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        data?.get(position)?.let { data ->
            holder.tvName.text = data.name
            holder.tvDesc.text = data.description
            holder.itemView.setOnClickListener {
                onClick.onNext(data.id)
            }
        }
    }

    inner class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvRecipeName)
        val tvDesc = view.findViewById<TextView>(R.id.tvRecipeDescription)
    }
}
