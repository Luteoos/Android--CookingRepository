package io.github.luteoos.cookrepo
//
// //import androidx.arch.core.executor.testing.InstantTaskExecutorRule
// //import io.github.luteoos.cookrepo.di.recipeModule
// //import io.github.luteoos.cookrepo.di.userProfileModule
// //import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
// //import io.reactivex.rxjava3.plugins.RxJavaPlugins
// //import io.reactivex.rxjava3.schedulers.Schedulers
// //import org.junit.Before
// //import org.junit.Rule
// //import org.junit.Test
// //import org.junit.runner.RunWith
// //import org.koin.android.ext.koin.androidContext
// //import org.koin.test.KoinTest
// //import org.mockito.junit.MockitoJUnitRunner
//
// @RunWith(MockitoJUnitRunner::class)
// class DITest : KoinTest {
//    @Rule
//    @JvmField
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setUp() {
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
//        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
//        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
//        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
//    }
//
//    @Test
//    fun checkDependencyGraph() {
// //        koinApplication {
// //            val mockContext = module(override = true) {
// //                androidContext(Mockito.mock(Context::class.java))
// //            }
// //            printLogger(Level.DEBUG)
// //            modules(recipeModule + userProfileModule + mockContext)
// //        }.checkModules()
//    }
// }
