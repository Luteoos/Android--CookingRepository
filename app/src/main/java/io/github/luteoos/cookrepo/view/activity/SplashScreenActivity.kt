package io.github.luteoos.cookrepo.view.activity

import android.content.Intent
import android.os.Bundle
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityNoVM
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.cookrepo.utils.getFirst
import io.realm.Realm
import timber.log.Timber

class SplashScreenActivity : ActivityNoVM(R.layout.activity_splash_screen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val a = RecipeRealm().create(session.username)
        Realm.getDefaultInstance().use {
            it.copyToRealmOrUpdate(a)
            Timber.i(it.getFirst(a.id!!, RecipeRealm::class.java)?.id.toString())
        }
        startMainScreenActivity()
    }

    private fun startMainScreenActivity(){
        val intent = Intent(this, MainScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }
}