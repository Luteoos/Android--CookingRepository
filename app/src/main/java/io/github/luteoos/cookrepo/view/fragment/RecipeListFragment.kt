package io.github.luteoos.cookrepo.view.fragment

import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RecipeListFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_recipe_list_screen) {

    override val viewModel: MainScreenViewModel by sharedViewModel()

}