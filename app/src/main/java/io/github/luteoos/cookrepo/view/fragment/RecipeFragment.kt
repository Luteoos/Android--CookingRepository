package io.github.luteoos.cookrepo.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamedabulgasem.loadingoverlay.LoadingOverlay
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipeCrumbs
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.data.view.RecipeViewData
import io.github.luteoos.cookrepo.utils.Parameters
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_recipe_screen.*
import javax.inject.Inject

class RecipeFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_recipe_screen) {

    @Inject
    lateinit var rvAdapter: RVAdapterRecipeCrumbs
    override val viewModel: MainScreenViewModel by lazy { ViewModelProvider(requireActivity(), provider).get(MainScreenViewModel::class.java) }
    private val loadingOverlay: LoadingOverlay by lazy {
        LoadingOverlay.with(
            context = requireActivity(),
            dimAmount = Parameters.OVERLAY_DIM
        )
    }
    private val args: RecipeFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingOverlay.show()
        setRV()
        setBindings()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecipe(args.recipeId)
    }

    private fun setRV() {
        rvRecipeCrumbs.apply {
            rvAdapter.setEditable(false)
            addItemDecoration(DividerItemDecoration(this@RecipeFragment.context, LinearLayoutManager.VERTICAL))
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@RecipeFragment.context)
        }
    }

    private fun setBindings() {
        btnEdit.setOnClickListener {
            with(findNavController()) {
                if (currentDestination?.id == R.id.recipeFragment)
                    navigate(RecipeFragmentDirections.actionRecipeFragmentToRecipeEditFragment(args.recipeId))
            }
        }
        viewModel.getRecipeLiveData().observe(
            viewLifecycleOwner,
            Observer { result ->
                setViewData(result)
                loadingOverlay.let { overlay ->
                    if (overlay.isShowing())
                        overlay.showFor(Parameters.OVERLAY_RECIPE_DELAY) // prevent annoying flcikering
//                        overlay.dismiss()
                }
            }
        )
    }

    private fun setViewData(data: RecipeViewData) {
        rvAdapter.updateData(data.crumbList)
        tvRecipeTitle.text = data.name
        tvRecipeDesc.text = data.description
    }
}
