package io.github.luteoos.cookrepo.dao

import io.github.luteoos.cookrepo.data.realm.IngredientAmountRealm
import io.github.luteoos.cookrepo.data.realm.IngredientRealm
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.cookrepo.data.realm.RecipeStepRealm
import io.github.luteoos.cookrepo.utils.getFirst
import io.realm.Realm

class RecipeDao {

    fun getRecipes(): MutableList<RecipeRealm> {
        val list = mutableListOf<RecipeRealm>()
        Realm.getDefaultInstance().use {
            list.addAll(
                it.copyFromRealm(
                    it.where(RecipeRealm::class.java)
                        .sort("name")
                        .findAll(),
                    0
                )
            )
        }
        return list
    }

    fun getRecipe(id: String): RecipeRealm? {
        var recipe: RecipeRealm? = null
        Realm.getDefaultInstance().use {
            it.getFirst(id, RecipeRealm::class.java)?.let { recipeLive ->
                recipe = it.copyFromRealm(recipeLive)
            }
        }
        return recipe
    }

    fun createRecipe(author: String): String {
        val recipe = RecipeRealm().create(author = author)
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.copyToRealm(recipe)
            }
        }
        return recipe.id
    }

    fun createIngredientAmount(id: String, author: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, RecipeRealm::class.java)?.ingredients?.add(
                    IngredientAmountRealm().create(
                        IngredientRealm().create(author = author),
                        author = author
                    )
                )
            }
        }
    }

    fun createStep(id: String, author: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, RecipeRealm::class.java)?.steps?.add(
                    RecipeStepRealm().create(author = author)
                )
            }
        }
    }

    fun updateRecipeTitle(id: String, title: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, RecipeRealm::class.java)?.let { recipe ->
                    recipe.name = title
                }
            }
        }
    }

    fun updateRecipeDesc(id: String, description: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, RecipeRealm::class.java)?.let { recipe ->
                    recipe.description = description
                }
            }
        }
    }

    fun updateRecipeStarred(id: String, starred: Boolean) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, RecipeRealm::class.java)?.let { recipe ->
                    recipe.starred = starred
                }
            }
        }
    }

    fun updateIngredientAmount(id: String, ingredientAmount: String, ingredientName: String, carted: Boolean) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, IngredientAmountRealm::class.java)?.let { amount ->
                    amount.amount = ingredientAmount
                    amount.ingredient?.name = ingredientName
                    amount.isInCart = carted
                }
            }
        }
    }

    fun updateStep(id: String, stepText: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, RecipeStepRealm::class.java)?.let { step ->
                    step.text = stepText
                }
            }
        }
    }

    fun deleteRecipe(id: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, RecipeRealm::class.java)?.cascadeDelete()
            }
        }
    }

    fun deleteIngredientAmount(id: String, recipeId: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, IngredientAmountRealm::class.java)?.cascadeDelete()
            }
        }
    }

    fun deleteStep(id: String, recipeId: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.getFirst(id, RecipeStepRealm::class.java)?.deleteFromRealm()
            }
        }
    }
}
