package io.github.luteoos.cookrepo.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipes
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_starred_list_screen.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StarredListFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_starred_list_screen) {

    override val viewModel: MainScreenViewModel by sharedViewModel()
    private val rvAdapter: RVAdapterRecipes by inject()

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
        rvAdapter.onClick.subscribe { id ->
            with(findNavController()) {
                if (currentDestination?.id == R.id.recipeListFragment)
                    navigate(StarredListFragmentDirections.actionStarredListFragmentToRecipeFragment(id))
            }
        }
    }

    private fun setBindings() {
        viewModel.getRecipesLiveData().observe(
            viewLifecycleOwner,
            Observer { results ->
                rvAdapter.updateData(results)
            }
        )
    }
}
