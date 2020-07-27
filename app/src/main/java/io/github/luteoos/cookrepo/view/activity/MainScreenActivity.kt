package io.github.luteoos.cookrepo.view.activity

import android.os.Bundle
import androidx.navigation.findNavController
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_main_screen.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : ActivityVM<MainScreenViewModel>(R.layout.activity_main_screen) {

    override val viewModel: MainScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()
    }

    private fun setBindings(){
        bottomNavBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.all_recipes -> openFragment(R.id.recipeListFragment)
                R.id.favs_recipes ->{}
                R.id.profile -> openFragment(R.id.userProfileFragment)
            }
            return@setOnNavigationItemSelectedListener true
        }

//        textView3.setOnClickListener {
//            this.findNavController(R.id.mainFragment).let {nav ->
//                if(nav.currentDestination?.id == R.id.recipeListFragment)
//                    nav.navigate(R.id.userProfileFragment) //bundle here
//                else
//                    nav.navigate(R.id.recipeListFragment)
//            }
//        }
    }

    private fun openFragment(fragmentId: Int){
        findNavController(R.id.mainFragment).let { navController ->
            if(navController.currentDestination?.id != fragmentId)
                navController.navigate(fragmentId)
        }
    }
}