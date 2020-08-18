package io.github.luteoos.cookrepo.view.activity

import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_main_screen.*

@AndroidEntryPoint
class MainScreenActivity : ActivityVM<MainScreenViewModel>(R.layout.activity_main_screen) {

    override val viewModel: MainScreenViewModel by viewModels()
    override fun onStart() {
        super.onStart()
        setBindings()
    }

    private fun setBindings() {
        with(findNavController(R.id.mainFragment)) { // has to be when View is created because of use FragmentContainer
            bottomNavBar.setupWithNavController(this)
        }
    }
}
