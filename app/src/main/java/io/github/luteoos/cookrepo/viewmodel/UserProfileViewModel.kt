package io.github.luteoos.cookrepo.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import io.github.luteoos.cookrepo.baseAbstract.BaseViewModel
import io.github.luteoos.cookrepo.utils.Session

class UserProfileViewModel
@ViewModelInject
constructor(private val session: Session) :
    BaseViewModel() {

    fun getUsername() = session.username

    fun updateUsername(newUserName: String) {
        session.username = newUserName
    }
}
