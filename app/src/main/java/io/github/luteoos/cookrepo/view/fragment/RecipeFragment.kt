package io.github.luteoos.cookrepo.view.fragment

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.like.LikeButton
import com.like.OnLikeListener
import dagger.hilt.android.AndroidEntryPoint
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.adapters.RVAdapterRecipeCrumbs
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.data.view.RecipeViewData
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_recipe_screen.*
import kotlinx.android.synthetic.main.view_recipe_title.*
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_recipe_screen) {

    @Inject
    lateinit var rvAdapter: RVAdapterRecipeCrumbs
    override val viewModel: MainScreenViewModel by activityViewModels()
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
            rvAdapter.setEditable(false)
            addItemDecoration(DividerItemDecoration(this@RecipeFragment.context, LinearLayoutManager.VERTICAL))
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@RecipeFragment.context)
        }
    }

    private fun setBindings() {
        likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                viewModel.updateRecipe(args.recipeId, starred = true)
            }

            override fun unLiked(likeButton: LikeButton?) {
                viewModel.updateRecipe(args.recipeId, starred = false)
            }
        })
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
            }
        )
    }

    private fun setViewData(data: RecipeViewData) {
        rvAdapter.updateData(data.crumbList)
        tvRecipeTitle.text = data.name
        tvRecipeDesc.text = data.description
        likeButton.isLiked = data.starred
        viewModel.stopLoading()
    }
}
