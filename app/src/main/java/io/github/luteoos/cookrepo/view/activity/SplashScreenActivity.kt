package io.github.luteoos.cookrepo.view.activity

import android.content.Intent
import android.os.Bundle
import io.github.luteoos.cookrepo.R
import io.github.luteoos.cookrepo.baseAbstract.ActivityNoVM

class SplashScreenActivity : ActivityNoVM(R.layout.activity_splash_screen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val a = RecipeRealm().create(session.username)
//        Realm.getDefaultInstance().use {realm ->
//            realm.executeTransaction {
//                realm.copyToRealmOrUpdate(a)
//            }
//            Timber.i(realm.getFirst(a.id!!, RecipeRealm::class.java)?.id.toString())
//        }
        startMainScreenActivity()
    }

    private fun startMainScreenActivity() {
        val intent = Intent(this, MainScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }
}
