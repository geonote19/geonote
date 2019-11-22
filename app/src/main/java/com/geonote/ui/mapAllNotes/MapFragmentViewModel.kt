package com.geonote.ui.mapAllNotes

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.geonote.data.AppRepository
import com.geonote.data.model.Event
import com.geonote.data.model.db.Note
import com.geonote.ui.base.BaseViewModel

class MapFragmentViewModel(
    application: Application,
    appRepository: AppRepository
) : BaseViewModel(application, appRepository) {

    private val noteDataMutable = MutableLiveData<Event<List<Note>>>()

    fun loadNotes() {
        requestWithLiveData(noteDataMutable) {
            mAppRepository.getNoteList()
        }
    }
}