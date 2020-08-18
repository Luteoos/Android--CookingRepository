package io.github.luteoos.cookrepo.view.fragment

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipes
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_recipe_list_screen.*
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_recipe_list_screen) {

    override val viewModel: MainScreenViewModel by activityViewModels()
    @Inject
    lateinit var rvAdapter: RVAdapterRecipes

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRV()
        setBindings()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecipesAll()
    }

    private fun setRV() {
        rvRecipes.apply {
            addItemDecoration(DividerItemDecoration(this@RecipeListFragment.context, LinearLayoutManager.VERTICAL))
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@RecipeListFragment.context)
        }
    }

    private fun setBindings() {
        rvAdapter.getOnClick().observe(
            viewLifecycleOwner,
            Observer { id ->
                id.get()?.let {
                    with(findNavController()) {
                        if (currentDestination?.id == R.id.recipeListFragment)
                            navigate(RecipeListFragmentDirections.actionRecipeListFragmentToRecipeFragment(it))
                    }
                }
            }
        )
        viewModel.getRecipesLiveData().observe(
            viewLifecycleOwner,
            Observer { results ->
                rvAdapter.updateData(results)
            }
        )
        fabAdd.setOnClickListener {
//            val a = RecipeRealm().create(session.username, name = "test splash", description = UUID.randomUUID().toString())
//            a.steps.add(RecipeStepRealm().create("test step 1", session.username))
//            a.steps.add(RecipeStepRealm().create("test step 2", session.username))
//            a.ingredients.add(IngredientAmountRealm().create(IngredientRealm().create("test meat 1", session.username), " 10kg", session.username))
//            a.ingredients.add(IngredientAmountRealm().create(IngredientRealm().create("test meat 2", session.username), " 5kg", session.username))
// //            a.starred = true
//            Realm.getDefaultInstance().executeTransaction {
//                it.copyToRealmOrUpdate(a)
//            }
            with(findNavController()) {
                if (currentDestination?.id == R.id.recipeListFragment)
                    navigate(
                        RecipeListFragmentDirections.actionRecipeListFragmentToRecipeEditFragment(
//                        a.id
                            viewModel.createRecipe()
                        )
                    )
            }
        }
    }
}
