package com.geonote.ui.list

import android.app.Application
import com.geonote.data.AppRepository
import com.geonote.ui.base.BaseViewModel

class ListFragmentViewModel(
    application: Application,
    appRepository: AppRepository
) : BaseViewModel(application, appRepository) {
}