package io.github.luteoos.cookrepo

import android.content.Context
import io.github.luteoos.cookrepo.di.mainScreenModule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito


class DITest : KoinTest {

    @Test
    fun checkDependencyGraph(){
        koinApplication {
            val mockContext = module(override = true) {
                androidContext(Mockito.mock(Context::class.java))
            }
            printLogger(Level.DEBUG)
            modules(mainScreenModule + mockContext)
        }.checkModules()
    }

}