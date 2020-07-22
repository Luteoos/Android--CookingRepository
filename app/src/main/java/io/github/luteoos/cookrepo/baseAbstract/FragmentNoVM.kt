package io.github.luteoos.cookrepo.baseAbstract

import android.view.WindowManager
import androidx.fragment.app.Fragment
import io.github.luteoos.cookrepo.utils.Session
import org.koin.android.ext.android.inject

abstract class FragmentNoVM(layoutId: Int) : Fragment(layoutId) {

    val session: Session by inject()

    /**
     * call to hide keyboard
     */
    fun hideKeyboard(){
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

}