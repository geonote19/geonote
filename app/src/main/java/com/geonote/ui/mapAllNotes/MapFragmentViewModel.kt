package com.geonote.ui.mapAllNotes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.geonote.data.AppRepository
import com.geonote.data.model.Event
import com.geonote.data.model.Marker
import com.geonote.data.model.db.Note
import com.geonote.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapFragmentViewModel(
    application: Application,
    appRepository: AppRepository
) : BaseViewModel(application, appRepository) {

    private val noteDataMutable = MutableLiveData<Event<List<Note>>>()

    val noteData: LiveData<Event<List<Note>>> = noteDataMutable

    fun updateMarkerLocation(marker: Marker) {
        viewModelScope.launch(Dispatchers.IO) {
            mAppRepository.updateMarkerLocation(marker)
        }
    }

    init {
        requestWithLiveData(noteDataMutable) {
            mAppRepository.getNoteList()
        }
    }
}