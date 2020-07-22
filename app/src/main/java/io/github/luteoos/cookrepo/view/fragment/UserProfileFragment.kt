package io.github.luteoos.cookrepo.view.fragment

import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserProfileFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_user_profile) {

    override val viewModel: MainScreenViewModel by sharedViewModel()
}