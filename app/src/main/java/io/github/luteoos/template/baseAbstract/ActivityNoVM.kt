package io.github.luteoos.template.baseAbstract

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import io.github.luteoos.template.utils.Session
import io.github.luteoos.mvvmbaselib.BaseActivityMVVM
import io.github.luteoos.mvvmbaselib.BaseActivityMVVMWithoutVM
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class ActivityNoVM : AppCompatActivity(){

    val session: Session by inject()

    /**
     * override and set layoutId here
     */
    abstract fun getLayoutID(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutID())
        setPortraitOrientation(true)
    }

    /**
     * true to portrait <-> default
     * false to landscape
     */
    fun setPortraitOrientation(isPortrait: Boolean = true) {
        requestedOrientation = when(isPortrait){
            true -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            false -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    /**
     * cal for hide keyboard from this activity
     */
    fun hideKeyboard(){
        if(this.currentFocus != null){
            val inputMng = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMng.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }
}