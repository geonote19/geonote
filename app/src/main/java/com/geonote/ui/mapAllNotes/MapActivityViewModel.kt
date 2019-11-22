package com.geonote.ui.mapAllNotes

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.geonote.data.AppRepository
import com.geonote.data.model.Event
import com.geonote.data.model.db.Note
import com.geonote.ui.base.BaseViewModel

class MapActivityViewModel(
    application: Application,
    appRepository: AppRepository
) : BaseViewModel(application, appRepository) {
}