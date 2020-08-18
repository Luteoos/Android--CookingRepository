package io.github.luteoos.cookrepo.view.activity

import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityNoVM

@AndroidEntryPoint
class SplashScreenActivity : ActivityNoVM(R.layout.activity_splash_screen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMainScreenActivity()
    }

    private fun startMainScreenActivity() {
        val intent = Intent(this, MainScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
