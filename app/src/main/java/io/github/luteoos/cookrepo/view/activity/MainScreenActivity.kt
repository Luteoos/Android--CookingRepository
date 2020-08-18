package io.github.luteoos.cookrepo.view.activity

import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mohamedabulgasem.loadingoverlay.LoadingOverlay
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityVM
import io.github.luteoos.cookrepo.data.view.LoadingState
import io.github.luteoos.cookrepo.utils.Parameters
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_main_screen.*

@AndroidEntryPoint
class MainScreenActivity : ActivityVM<MainScreenViewModel>(R.layout.activity_main_screen) {

    override val viewModel: MainScreenViewModel by viewModels()
    private val loadingOverlay: LoadingOverlay by lazy {
        LoadingOverlay.with(
            context = this,
            dimAmount = Parameters.OVERLAY_DIM
        )
    }
    override fun onStart() {
        super.onStart()
        setBindings()
    }

    private fun setBindings() {
        with(findNavController(R.id.mainFragment)) { // has to be when View is created because of use FragmentContainer
            bottomNavBar.setupWithNavController(this)
        }
        viewModel.getLoadingState().observe(
            this,
            Observer { state ->
                handleLoadingOverlay(state)
            }
        )
    }

    private fun handleLoadingOverlay(state: LoadingState) {
        when (state) {
            LoadingState.StartLoading -> loadingOverlay.show()
            LoadingState.StopLoading ->
                if (loadingOverlay.isShowing())
                    loadingOverlay.showFor(Parameters.OVERLAY_DELAY)
        }
    }
}
