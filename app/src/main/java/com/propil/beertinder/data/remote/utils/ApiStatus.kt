package com.propil.beertinder.data.remote.utils

data class ApiStatus<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): ApiStatus<T> = ApiStatus(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): ApiStatus<T> =
            ApiStatus(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): ApiStatus<T> = ApiStatus(status = Status.LOADING, data = data, message = null)
    }
}