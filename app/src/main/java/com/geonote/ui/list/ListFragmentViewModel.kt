package com.geonote.ui.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geonote.data.AppRepository
import com.geonote.data.model.Event
import com.geonote.data.model.db.Note
import com.geonote.ui.base.BaseViewModel

class ListFragmentViewModel(
    application: Application,
    appRepository: AppRepository
) : BaseViewModel(application, appRepository) {

    private val noteDataListMutable = MutableLiveData<Event<List<Note>>>()

    val noteDataList: LiveData<Event<List<Note>>> = noteDataListMutable

    init {
        load()
    }

    fun load() {
        requestWithLiveData(noteDataListMutable) {
            mAppRepository.getNoteList()
        }
    }
}