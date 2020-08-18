package io.github.luteoos.cookrepo.baseAbstract

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

abstract class FragmentNoVM(layoutId: Int) : Fragment(layoutId) {

    /**
     * call to hide keyboard
     */
    fun hideKeyboard(view: View) {
        activity?.let {
            (it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
