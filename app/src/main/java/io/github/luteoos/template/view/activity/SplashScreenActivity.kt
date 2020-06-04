package io.github.luteoos.template.view.activity

import android.content.Intent
import android.os.Bundle
import io.github.luteoos.template.R
import io.github.luteoos.template.baseAbstract.ActivityNoVM
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : ActivityNoVM() {

    override fun getLayoutID(): Int = R.layout.activity_splash_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMainScreenActivity()
    }

    private fun startMainScreenActivity(){
        TODO("NOT IMPLEMENTED")
    }
}