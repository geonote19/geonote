package com.geonote.utils

import androidx.lifecycle.*

fun <T> LiveData<T>.observe(owner: LifecycleOwner, listener: (T) -> Unit) {
    this.observe(owner, Observer {
        it ?: return@Observer
        listener(it)
    })
}

fun <T : ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T):
        (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel?> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}

