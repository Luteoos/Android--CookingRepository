package io.github.luteoos.cookrepo.baseAbstract

import androidx.lifecycle.Observer
import io.github.luteoos.cookrepo.viewmodel.factory.ViewModelProviderFactory
import io.github.luteoos.mvvmbaselib.BaseViewModel
import io.github.luteoos.mvvmbaselib.Event
import javax.inject.Inject

abstract class FragmentVM<T : BaseViewModel>(layoutId: Int) : FragmentNoVM(layoutId) {

    @Inject
    lateinit var provider: ViewModelProviderFactory

    /**
     * init it with getViewModel<T>(this)
     */
    abstract val viewModel: T

    /**
     * invoke when VM is assigned
     */
    fun connectToVMMessage() {
        viewModel.message().observe(this, Observer { onVMMessage(it) })
    }

    /**
     * override it to handle message from ViewModel
     */
    open fun onVMMessage(msg: Event<Int>) {
    }
}
