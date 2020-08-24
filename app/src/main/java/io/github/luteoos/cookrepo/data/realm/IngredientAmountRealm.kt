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
    var amount: String = ""
    var isInCart: Boolean = false
    @Ignore
    var isCompleted: Boolean = false

    fun create(ingredientRealm: IngredientRealm? = null, amount: String = "", author: String): IngredientAmountRealm {
        this.create(author)
        ingredientRealm?.let {
            this.ingredient = ingredientRealm
        } ?: run {
            this.ingredient = IngredientRealm().create(author = author)
        }
        this.amount = amount
        return this
    }

    fun cascadeDelete() {
        ingredient?.deleteFromRealm()
        deleteFromRealm()
    }
}
