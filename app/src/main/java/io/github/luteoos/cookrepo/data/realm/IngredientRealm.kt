package io.github.luteoos.cookrepo.data.realm

import io.github.luteoos.cookrepo.utils.create
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class IngredientRealm : RealmObject(), BaseRealmInterface {

    @PrimaryKey
    override lateinit var id: String
    override lateinit var created: String
    override lateinit var author: String

    var name: String? = null

    fun create(name: String = "", author: String): IngredientRealm {
        this.create(author)
        this.name = name
        return this
    }
}
