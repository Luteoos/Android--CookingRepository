package io.github.luteoos.cookrepo.baseAbstract

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import io.github.luteoos.cookrepo.utils.Session
import org.koin.android.ext.android.inject

abstract class FragmentNoVM(layoutId: Int) : Fragment(layoutId) {

    val session: Session by inject()

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
