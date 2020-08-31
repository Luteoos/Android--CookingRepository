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
import io.github.luteoos.cookrepo.utils.Parameters
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_starred_list_screen.*
import javax.inject.Inject

@AndroidEntryPoint
class StarredListFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_starred_list_screen) {

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
        viewModel.getRecipesStarred()
    }

    private fun setRV() {
        rvRecipesStarred.apply {
            addItemDecoration(DividerItemDecoration(this@StarredListFragment.context, LinearLayoutManager.VERTICAL))
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@StarredListFragment.context)
        }
    }

    private fun setBindings() {
        rvAdapter.getOnClick().observe(
            viewLifecycleOwner,
            Observer { event ->
                event.get()?.let { pair ->
                    when (pair.second) {
                        Parameters.RECIPE_ADAPTER_OPEN -> navigateToRecipe(pair.first)
                        Parameters.RECIPE_ADAPTER_DELETE -> viewModel.deleteRecipe(pair.first, true)
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
    }

    private fun navigateToRecipe(id: String) {
        with(findNavController()) {
            if (currentDestination?.id == R.id.starredListFragment) {
                viewModel.startLoading()
                navigate(StarredListFragmentDirections.actionStarredListFragmentToRecipeFragment(id))
            }
        }
    }
}
