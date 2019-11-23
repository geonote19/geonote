package com.geonote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geonote.data.AppRepository
import com.geonote.ui.MainActivityViewModel
import com.geonote.ui.detail.DetailFragmentViewModel
import com.geonote.ui.detail.edit.EditDetailFragmentViewModel
import com.geonote.ui.detail.mapDetails.DetailFragmentMapViewModel
import com.geonote.ui.list.ListFragmentViewModel
import com.geonote.ui.mapAllNotes.MapActivityViewModel
import com.geonote.ui.mapAllNotes.MapFragmentViewModel

class ViewModelFactory(
        private val mApplication: Application,
        private val mAppRepository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ListFragmentViewModel::class.java) -> {
                return ListFragmentViewModel(mApplication, mAppRepository) as T
            }
            modelClass.isAssignableFrom(DetailFragmentViewModel::class.java) -> {
                return DetailFragmentViewModel(mApplication, mAppRepository) as T
            }
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                return MainActivityViewModel(mApplication, mAppRepository) as T
            }
            modelClass.isAssignableFrom(MapActivityViewModel::class.java) -> {
                return MapActivityViewModel(mApplication, mAppRepository) as T
            }
            modelClass.isAssignableFrom(MapFragmentViewModel::class.java) -> {
                return MapFragmentViewModel(mApplication, mAppRepository) as T
            }
            modelClass.isAssignableFrom(EditDetailFragmentViewModel::class.java) -> {
                return EditDetailFragmentViewModel(mApplication, mAppRepository) as T
            }
            modelClass.isAssignableFrom(DetailFragmentMapViewModel::class.java) -> {
                return DetailFragmentMapViewModel(mApplication, mAppRepository) as T
            }
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