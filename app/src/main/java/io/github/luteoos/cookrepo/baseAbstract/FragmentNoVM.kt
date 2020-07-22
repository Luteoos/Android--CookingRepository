package io.github.luteoos.cookrepo.baseAbstract

import android.view.WindowManager
import androidx.fragment.app.Fragment

abstract class FragmentNoVM(layoutId: Int) : Fragment(layoutId) {

    /**
     * call to hide keyboard
     */
    fun hideKeyboard(){
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

}