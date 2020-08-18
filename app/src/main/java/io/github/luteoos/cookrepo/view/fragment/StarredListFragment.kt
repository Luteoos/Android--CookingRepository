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
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_starred_list_screen.*
import javax.inject.Inject

class StarredListFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_starred_list_screen) {

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
            Observer { id ->
                id.get()?.let {
                    navigateToRecipe(it)
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
