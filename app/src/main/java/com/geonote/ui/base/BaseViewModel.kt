package com.geonote.ui.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.geonote.data.model.exception.EmptyValueException
import com.geonote.data.AppRepository
import com.geonote.data.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseViewModel(
    application: Application,
    protected val mAppRepository: AppRepository
) : AndroidViewModel(application) {

    fun <T> requestWithLiveData(
        liveData: MutableLiveData<Event<T>>,
        observableField: ObservableField<T>? = null,
        request: suspend () -> T?
    ) {
        liveData.postValue(Event.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = request()
                if (data != null) {
                    liveData.postValue(Event.success(data))
                    observableField?.set(data)
                } else {
                    liveData.postValue(Event.error(EmptyValueException()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(Event.error(e))
            }
        }
    }

    fun <T> requestWithCallback(
        request: suspend () -> T?,
        response: (Event<T>) -> Unit
    ) {
        response(Event.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = request()
                launch(Dispatchers.Main) {
                    if (data != null) {
                        response(Event.success(data))
                    } else {
                        response(Event.error(EmptyValueException()))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    response(Event.error(e))
                }
            }
        }
    }
}