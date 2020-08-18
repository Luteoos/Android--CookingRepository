package io.github.luteoos.cookrepo.view.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mohamedabulgasem.loadingoverlay.LoadingOverlay
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityVM
import io.github.luteoos.cookrepo.data.view.LoadingState
import io.github.luteoos.cookrepo.utils.Parameters
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreenActivity : ActivityVM<MainScreenViewModel>(R.layout.activity_main_screen) {

    override val viewModel: MainScreenViewModel by lazy { ViewModelProvider(this, provider).get(MainScreenViewModel::class.java) } // ViewModelProviders.of(this, provider).get(MainScreenViewModel::class.java) } // by viewModel()
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
