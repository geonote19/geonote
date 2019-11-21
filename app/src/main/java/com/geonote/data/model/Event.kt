package com.geonote.data.model

data class Event<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {

    // Methods for databinding
    val hasError: Boolean
        get() = status == Status.ERROR

    val isSuccess: Boolean
        get() = status == Status.SUCCESS

    val isLoading: Boolean
        get() = status == Status.LOADING

    companion object {
        fun <T> loading(): Event<T> =
            Event(Status.LOADING)
        fun <T> success(data: T?): Event<T> =
            Event(Status.SUCCESS, data)
        fun <T> error(error: Throwable?): Event<T> =
            Event(Status.ERROR, error = error)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}