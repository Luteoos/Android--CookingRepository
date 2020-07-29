package io.github.luteoos.cookrepo.viewmodel

import io.github.luteoos.cookrepo.baseAbstract.BaseViewModel
import io.github.luteoos.cookrepo.utils.Session

class UserProfileViewModel : BaseViewModel() {

    fun getUsername(session: Session) = session.username

    fun updateUsername(session: Session, newUserName: String) {
        session.username = newUserName
    }
}
