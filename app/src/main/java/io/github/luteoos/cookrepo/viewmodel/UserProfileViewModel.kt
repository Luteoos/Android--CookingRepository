package io.github.luteoos.cookrepo.viewmodel

import io.github.luteoos.cookrepo.baseAbstract.BaseViewModel
import io.github.luteoos.cookrepo.utils.Session
import javax.inject.Inject

class UserProfileViewModel
@Inject
constructor(private val session: Session) :
    BaseViewModel() {

    fun getUsername() = session.username

    fun updateUsername(newUserName: String) {
        session.username = newUserName
    }
}
