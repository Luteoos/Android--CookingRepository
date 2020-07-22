package io.github.luteoos.cookrepo.baseAbstract

import androidx.lifecycle.Observer
import io.github.luteoos.mvvmbaselib.BaseViewModel
import io.github.luteoos.mvvmbaselib.Event

abstract class ActivityVM<T: BaseViewModel>(layoutId: Int) : ActivityNoVM(layoutId) {

    abstract val viewModel: T


    /**
     * To invoke when VM is assigned
     */
    fun connectToVMMessage(){
        viewModel.message().observe(this, Observer { onVMMessage(it) })
    }

    /**
     * override it to handle message from ViewModel
     *
     */
    open fun onVMMessage(msg: Event<Int>){
    }

}