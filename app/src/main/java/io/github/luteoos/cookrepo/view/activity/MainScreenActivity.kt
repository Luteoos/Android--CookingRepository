package io.github.luteoos.cookrepo.view.activity

import android.os.Bundle
import androidx.navigation.findNavController
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_main_screen.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : ActivityVM<MainScreenViewModel>() {

    override val viewModel: MainScreenViewModel by viewModel()

    override fun getLayoutID(): Int = R.layout.activity_main_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()
    }

    private fun setBindings(){
        textView3.setOnClickListener {
            this.findNavController(R.id.mainFragment).let {nav ->
                if(nav.currentDestination?.id == R.id.recipeListFragment)
                    nav.navigate(R.id.userProfileFragment)
                else
                    nav.navigate(R.id.recipeListFragment)
            }
        }
    }
}