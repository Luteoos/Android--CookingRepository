package io.github.luteoos.cookrepo.view.fragment

import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : FragmentVM<UserProfileViewModel>(R.layout.fragment_user_profile) {

    override val viewModel: UserProfileViewModel by lazy {
        ViewModelProvider(requireActivity(), provider).get(
            UserProfileViewModel::class.java
        )
    }

    override fun onStart() {
        super.onStart()
        usernameInput.editText?.setText(viewModel.getUsername())
        usernameInput.editText?.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                this.hideKeyboard(view)
            }
        }
        usernameInput.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateUsername(text.toString())
        }
    }
}
