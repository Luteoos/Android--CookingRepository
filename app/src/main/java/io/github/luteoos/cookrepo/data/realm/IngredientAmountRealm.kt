package io.github.luteoos.cookrepo.data.realm

import io.github.luteoos.cookrepo.utils.create
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class IngredientAmountRealm : RealmObject(), BaseRealmInterface {

    @PrimaryKey
    override lateinit var id: String
    override lateinit var created: String
    override lateinit var author: String

    var ingredient: IngredientRealm? = null
    var amount: Int = 0
//    lateinit var unit : String =\
    @Ignore
    var isCompleted: Boolean = false

    fun create(ingredientRealm: IngredientRealm, amount: Int, author: String): IngredientAmountRealm {
        this.create(author)
        this.ingredient = ingredientRealm
        this.amount = amount
        return this
    }
}
