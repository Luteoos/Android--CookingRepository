package io.github.luteoos.cookrepo.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipes
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.data.realm.IngredientAmountRealm
import io.github.luteoos.cookrepo.data.realm.IngredientRealm
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.cookrepo.data.realm.RecipeStepRealm
import io.github.luteoos.cookrepo.utils.Session
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_recipe_list_screen.*
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class RecipeListFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_recipe_list_screen) {

    @Inject
    lateinit var session: Session // TEMP for testing ease during early development
    override val viewModel: MainScreenViewModel by lazy { ViewModelProvider(requireActivity(), provider).get(MainScreenViewModel::class.java) }
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
        rvAdapter.onClick.subscribe(
            { id ->
                with(findNavController()) {
                    if (currentDestination?.id == R.id.recipeListFragment)
                        navigate(RecipeListFragmentDirections.actionRecipeListFragmentToRecipeFragment(id))
                }
            },
            {
                Timber.e(it)
            }
        )
    }

    private fun setBindings() {
        viewModel.getRecipesLiveData().observe(
            viewLifecycleOwner,
            Observer { results ->
                rvAdapter.updateData(results)
            }
        )
        fabAdd.setOnClickListener {
            val a = RecipeRealm().create(session.username, name = "test splash", description = UUID.randomUUID().toString())
            a.steps.add(RecipeStepRealm().create("test step 1", session.username))
            a.steps.add(RecipeStepRealm().create("test step 2", session.username))
            a.ingredients.add(IngredientAmountRealm().create(IngredientRealm().create("test meat 1", session.username), " 10kg", session.username))
            a.ingredients.add(IngredientAmountRealm().create(IngredientRealm().create("test meat 2", session.username), " 5kg", session.username))
//            a.starred = true
            Realm.getDefaultInstance().executeTransaction {
                it.copyToRealmOrUpdate(a)
            }
            with(findNavController()) {
                if (currentDestination?.id == R.id.recipeListFragment)
                    navigate(RecipeListFragmentDirections.actionRecipeListFragmentToRecipeEditFragment(a.id))
            }
        }
    }
}
