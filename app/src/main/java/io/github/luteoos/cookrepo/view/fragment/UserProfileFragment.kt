package io.github.luteoos.cookrepo.view.fragment

import androidx.core.widget.doOnTextChanged
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileFragment : FragmentVM<UserProfileViewModel>(R.layout.fragment_user_profile) {

    override val viewModel: UserProfileViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        usernameInput.editText?.setText(viewModel.getUsername(session))
        usernameInput.editText?.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
//                view.clearFocus()
                this.hideKeyboard(view)
            }
        }
        usernameInput.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateUsername(session, text.toString())
        }
    }
}
