package io.github.luteoos.cookrepo.view.fragment

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.FragmentVM
import io.github.luteoos.cookrepo.viewmodel.CartViewModel

@AndroidEntryPoint
class CartFragment : FragmentVM<CartViewModel>(R.layout.fragment_cart_screen) {

    override val viewModel: CartViewModel by viewModels()
}
