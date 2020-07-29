package io.github.luteoos.cookrepo.baseAbstract

import android.content.Context
import android.content.pm.ActivityInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import io.github.luteoos.cookrepo.utils.Session
import org.koin.android.ext.android.inject

abstract class ActivityNoVM(layoutId: Int) : AppCompatActivity(layoutId) {

    val session: Session by inject()

    /**
     * true to portrait <-> default
     * false to landscape
     */
    fun setPortraitOrientation(isPortrait: Boolean = true) {
        requestedOrientation = when (isPortrait) {
            true -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            false -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    /**
     * cal for hide keyboard from this activity
     */
    fun hideKeyboard(view: View) {
        (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.windowToken, 0)
    }
}
