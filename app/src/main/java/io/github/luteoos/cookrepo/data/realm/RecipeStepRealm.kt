package io.github.luteoos.cookrepo.data.realm

import io.github.luteoos.cookrepo.utils.create
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class RecipeStepRealm : RealmObject(), BaseRealmInterface {

    @PrimaryKey
    override lateinit var id: String
    override lateinit var created: String
    override lateinit var author: String

    lateinit var text: String
    @Ignore
    var isCompleted: Boolean = false

    fun create(text: String, author: String): RecipeStepRealm {
        this.create(author)
        this.text = text
        return this
    }
}
