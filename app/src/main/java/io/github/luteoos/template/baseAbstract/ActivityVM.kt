package io.github.luteoos.template.baseAbstract

import android.os.Bundle
import androidx.lifecycle.Observer
import io.github.luteoos.mvvmbaselib.BaseViewModel
import io.github.luteoos.mvvmbaselib.Event

abstract class ActivityVM<T: BaseViewModel> : ActivityNoVM() {

    abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPortraitOrientation(true)
    }


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