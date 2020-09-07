package io.github.luteoos.cookrepo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.luteoos.cookrepo.dao.RecipeDao
import io.github.luteoos.cookrepo.data.realm.RecipeRealm
import io.github.luteoos.cookrepo.data.wrapper.RecipeWrapper
import io.github.luteoos.cookrepo.data.wrapper.RecipesListWrapper
import io.github.luteoos.cookrepo.utils.Session
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipeRepositoryTest {
    private val recipeIdTest = "test"
    private val recipeTest = RecipeRealm().apply {
        id = recipeIdTest
        author = "RecipeRepositoryTest"
        name = "test"
        description = ""
        starred = false
    }
    private val recipeTestStarred = RecipeRealm().apply {
        id = "$recipeIdTest starred"
        author = "RecipeRepositoryTest"
        name = "testStarred"
        description = ""
        starred = true
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val sessionMock: Session = Mockito.mock(Session::class.java, Mockito.RETURNS_DEEP_STUBS)
    private val recipeDaoMock: RecipeDao = Mockito.mock(RecipeDao::class.java)

    private val repo: RecipeRepository = RecipeRepository(sessionMock, recipeDaoMock)

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
//        Mockito.`when`(sessionMock.username).thenReturn("RecipeRepositoryTest")
    }

    @Test
    fun getRecipe_RecipeExist_ReturnWrapperWithRecipe() {
        Mockito.`when`(recipeDaoMock.getRecipe(recipeIdTest)).thenReturn(recipeTest)
        val observer = TestObserver.create<RecipeWrapper>()
        repo.getRecipeObservable().subscribe(observer)
        repo.getRecipe(recipeIdTest)
        observer.hasSubscription()
        observer.assertNoErrors()
        observer.awaitCount(1)
        observer.assertValue {
            it.isSuccess && it.result != null
        }
//        val observer = com.jraska.livedata.TestObserver.create<>()
//        MainScreenViewModel().getRecipeLiveData().test().observeForever()
    }

    @Test
    fun getRecipe_RecipeNonExisting_ReturnWrappedResultNull() {
        Mockito.`when`(recipeDaoMock.getRecipe(recipeIdTest)).thenReturn(null)
        val observer = TestObserver.create<RecipeWrapper>()
        repo.getRecipeObservable().subscribe(observer)
        repo.getRecipe(recipeIdTest)
        observer.hasSubscription()
        observer.assertNoErrors()
        observer.awaitCount(1)
        observer.assertValue {
            !it.isSuccess && it.result == null
        }
    }

    @Test
    fun getRecipesAll_RecipesOneExisting_ReturnWrapperWithList() {
        Mockito.`when`(recipeDaoMock.getRecipes()).thenReturn(mutableListOf(recipeTest))
        val observer = TestObserver.create<RecipesListWrapper>()
        repo.getRecipesObservable().subscribe(observer)
        repo.getRecipesAll()
        observer.hasSubscription()
        observer.assertNoErrors()
        observer.awaitCount(1)
        observer.assertValue {
            it.isSuccess && it.result != null && it.result!!.size != 0
        }
    }

    @Test
    fun getRecipesAll_RecipesMultipleExisting_ReturnWrapperWithList() {
        Mockito.`when`(recipeDaoMock.getRecipes()).thenReturn(mutableListOf(recipeTest, recipeTestStarred))
        val observer = TestObserver.create<RecipesListWrapper>()
        repo.getRecipesObservable().subscribe(observer)
        repo.getRecipesAll()
        observer.hasSubscription()
        observer.assertNoErrors()
        observer.awaitCount(1)
        observer.assertValue {
            it.isSuccess && it.result != null && it.result!!.size != 0
        }
    }

    @Test
    fun getRecipesAll_RecipesNoneExisting_ReturnWrapperWithList() {
        Mockito.`when`(recipeDaoMock.getRecipes()).thenReturn(mutableListOf())
        val observer = TestObserver.create<RecipesListWrapper>()
        repo.getRecipesObservable().subscribe(observer)
        repo.getRecipesAll()
        observer.hasSubscription()
        observer.assertNoErrors()
        observer.awaitCount(1)
        observer.assertValue {
            it.isSuccess && it.result != null && it.result!!.size == 0
        }
    }

    @Test
    fun getRecipesStarred_RecipesMultipleExisting_ReturnWrapperWithList() {
        Mockito.`when`(recipeDaoMock.getRecipes()).thenReturn(mutableListOf(recipeTest, recipeTestStarred))
        val observer = TestObserver.create<RecipesListWrapper>()
        repo.getRecipesObservable().subscribe(observer)
        repo.getRecipesStarred()
        observer.hasSubscription()
        observer.assertNoErrors()
        observer.awaitCount(1)
        observer.assertValue {
            it.isSuccess && it.result != null && it.result!!.size != 0
        }
    }

    @Test
    fun getRecipesStarred_RecipesOneExisting_ReturnWrapperWithEmptyList() {
        Mockito.`when`(recipeDaoMock.getRecipes()).thenReturn(mutableListOf(recipeTest))
        val observer = TestObserver.create<RecipesListWrapper>()
        repo.getRecipesObservable().subscribe(observer)
        repo.getRecipesStarred()
        observer.hasSubscription()
        observer.assertNoErrors()
        observer.awaitCount(1)
        observer.assertValue {
            it.isSuccess && it.result != null && it.result!!.size == 0
        }
    }

    @Test
    fun getRecipesStarred_RecipesNoneExisting_ReturnWrapperWithList() {
        Mockito.`when`(recipeDaoMock.getRecipes()).thenReturn(mutableListOf())
        val observer = TestObserver.create<RecipesListWrapper>()
        repo.getRecipesObservable().subscribe(observer)
        repo.getRecipesStarred()
        observer.hasSubscription()
        observer.assertNoErrors()
        observer.awaitCount(1)
        observer.assertValue {
            it.isSuccess && it.result != null && it.result!!.size == 0
        }
    }
}
