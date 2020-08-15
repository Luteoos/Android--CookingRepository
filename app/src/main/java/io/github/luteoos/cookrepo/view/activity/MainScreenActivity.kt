package io.github.luteoos.cookrepo.view.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import io.github.luteoos.cookrepo.viewmodel.factory.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main_screen.*
import javax.inject.Inject

class MainScreenActivity : ActivityVM<MainScreenViewModel>(R.layout.activity_main_screen) {

    override val viewModel: MainScreenViewModel by lazy { ViewModelProvider(this, provider).get(MainScreenViewModel::class.java) } // ViewModelProviders.of(this, provider).get(MainScreenViewModel::class.java) } // by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()
    }

    private fun setBindings() {
        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.all_recipes -> openFragment(R.id.recipeListFragment)
                R.id.favs_recipes -> openFragment(R.id.starredListFragment)
                R.id.profile -> openFragment(R.id.userProfileFragment)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun openFragment(fragmentId: Int) {
        findNavController(R.id.mainFragment).let { navController ->
            if (navController.currentDestination?.id != fragmentId)
                navController.navigate(fragmentId)
        }
    }
}
