package io.github.luteoos.cookrepo.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipeCrumbs
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.data.view.RecipeViewData
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_recipe_screen.*
import javax.inject.Inject

class RecipeFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_recipe_screen) {

    override val viewModel: MainScreenViewModel by lazy { ViewModelProvider(requireActivity(), provider).get(MainScreenViewModel::class.java) }
    @Inject
    lateinit var rvAdapter: RVAdapterRecipeCrumbs
    private val args: RecipeFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRV()
        setBindings()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecipe(args.recipeId)
    }

    private fun setRV() {
        rvRecipeCrumbs.apply {
            addItemDecoration(DividerItemDecoration(this@RecipeFragment.context, LinearLayoutManager.VERTICAL))
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@RecipeFragment.context)
        }
    }

    private fun setBindings() {
        viewModel.getRecipeLiveData().observe(
            viewLifecycleOwner,
            Observer { result ->
                setViewData(result)
            }
        )
    }

    private fun setViewData(data: RecipeViewData) {
        rvAdapter.updateData(data.crumbList)
        tvRecipeTitle.editText?.setText(data.name)
        tvRecipeDesc.editText?.setText(data.description)
    }
}
