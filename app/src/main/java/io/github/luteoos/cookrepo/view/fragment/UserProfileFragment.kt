package io.github.luteoos.cookrepo.view.fragment

import androidx.core.widget.doOnTextChanged
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserProfileFragment : FragmentVM<MainScreenViewModel>(R.layout.fragment_user_profile) {

    override val viewModel: MainScreenViewModel by sharedViewModel()

    override fun onStart() {
        super.onStart()
        usernameInput.editText?.setText(session.username)
        usernameInput.editText?.doOnTextChanged { text, _, _, _ ->
            session.username = text.toString()
        }
    }
}
