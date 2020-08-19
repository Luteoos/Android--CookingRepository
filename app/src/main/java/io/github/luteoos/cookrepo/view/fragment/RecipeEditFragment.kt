package io.github.luteoos.cookrepo.view.fragment

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipeCrumbs
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.data.view.RecipeViewData
import io.github.luteoos.cookrepo.utils.setTextChanged
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_recipe_edit_screen.*
import kotlinx.android.synthetic.main.view_recipe_title_edit.*
import javax.inject.Inject

@AndroidEntryPoint
class RecipeEditFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_recipe_edit_screen) {

    @Inject
    lateinit var rvAdapter: RVAdapterRecipeCrumbs
    override val viewModel: MainScreenViewModel by activityViewModels()
    private val args: RecipeEditFragmentArgs by navArgs()

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
            rvAdapter.setEditable(true)
            addItemDecoration(DividerItemDecoration(this@RecipeEditFragment.context, LinearLayoutManager.VERTICAL))
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@RecipeEditFragment.context)
        }
    }

    private fun setBindings() {
        btnSave.setOnClickListener {
            with(findNavController()) {
                this@RecipeEditFragment.hideKeyboard(it)
                popBackStack()
            }
        }
        tvRecipeTitle.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateRecipe(args.recipeId, title = text.toString())
        }
        tvRecipeDesc.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateRecipe(args.recipeId, description = text.toString())
        }
        rvAdapter.getItemUpdate().observe(
            viewLifecycleOwner,
            Observer { crumb ->
                crumb.get()?.let {
                    viewModel.updateRecipe(args.recipeId, it.first, it.second)
                }
            }
        )
        viewModel.getRecipeLiveData().observe(
            viewLifecycleOwner,
            Observer { result ->
                setViewData(result)
            }
        )
    }

    private fun setViewData(data: RecipeViewData) {
        rvAdapter.updateData(data.crumbList)
        tvRecipeTitle.editText?.setTextChanged(data.name)
        tvRecipeDesc.editText?.setTextChanged(data.description)
        viewModel.stopLoading()
    }
}
