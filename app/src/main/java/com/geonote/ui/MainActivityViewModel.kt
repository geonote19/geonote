package com.geonote.ui

import android.app.Application
import com.geonote.data.AppRepository
import com.geonote.ui.base.BaseViewModel

class MainActivityViewModel(
    application: Application,
    appRepository: AppRepository
) : BaseViewModel(application, appRepository) {
}