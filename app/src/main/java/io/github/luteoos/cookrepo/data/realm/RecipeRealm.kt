package io.github.luteoos.cookrepo.data.realm

import io.github.luteoos.cookrepo.utils.create
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RecipeRealm : RealmObject(), BaseRealmInterface {

    @PrimaryKey
    override var id: String? = null
    override var created: String? = null
    override var author: String? = null

    fun create(author: String): RecipeRealm {
        this.create(authorName = author)
        return this
    }
}
