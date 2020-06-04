package io.github.luteoos.template.baseAbstract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment

abstract class FragmentNoVM : Fragment() {

    /**
     * override and set layoutId here
     */
    abstract fun getLayoutID(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutID(), container, false)
    }

    /**
     * call to hide keyboard
     */
    fun hideKeyboard(){
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

}