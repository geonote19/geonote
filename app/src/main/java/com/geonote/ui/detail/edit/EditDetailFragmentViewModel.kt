package com.geonote.ui.detail.edit

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.geonote.data.AppRepository
import com.geonote.data.model.db.Note
import com.geonote.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDetailFragmentViewModel(
    application: Application,
    appRepository: AppRepository
) : BaseViewModel(application, appRepository) {

    val note = MutableLiveData<Note>()

    fun save(noteToSave: Note) {

        viewModelScope.launch(Dispatchers.IO) {
            mAppRepository.mergeNote(noteToSave)
        }

    }
}