package com.geonote.ui.detail.mapDetails

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.geonote.data.AppRepository
import com.geonote.data.model.Event
import com.geonote.data.model.db.Note
import com.geonote.ui.base.BaseViewModel

class DetailFragmentMapViewModel (application: Application,
                                  appRepository: AppRepository
) : BaseViewModel(application, appRepository)  {

    private val noteDataMutable = MutableLiveData<Event<Note>>()
    val note = ObservableField<Note>()

    fun loadNote(id: Long) {
        requestWithLiveData(noteDataMutable, note) {
            mAppRepository.getNoteById(id)
        }
    }

}