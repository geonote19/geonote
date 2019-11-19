package com.geonote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geonote.data.AppRepository

class ViewModelFactory(
        private val mApplication: Application,
        private val mAppRepository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
//            modelClass.isAssignableFrom(FragmentViewModel::class.java) -> {
//                return FragmentViewModel(mApplication, mAppRepository) as T
//            }
        }
        throw IllegalArgumentException("Unknown view model class: " + modelClass.name)
    }

    companion object {

        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application = App.INSTANCE): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(application,
                                AppRepository.getInstance(application.applicationContext))
                    }
                }
            }
            return INSTANCE!!
        }
    }
}