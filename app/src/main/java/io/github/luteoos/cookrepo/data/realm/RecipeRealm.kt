package io.github.luteoos.cookrepo.data.realm

import io.github.luteoos.cookrepo.utils.create
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RecipeRealm : RealmObject(), BaseRealmInterface {

    @PrimaryKey
    override lateinit var id: String
    override lateinit var created: String
    override lateinit var author: String

    var name: String = ""
    var description: String = ""
    var ingredients: RealmList<IngredientAmountRealm> = RealmList()
    var steps: RealmList<RecipeStepRealm> = RealmList()

    fun create(author: String, name: String, description: String = ""): RecipeRealm {
        this.create(authorName = author)
        this.name = name
        this.description = description
        return this
    }
}
