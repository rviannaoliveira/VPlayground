package com.rviannaoliveira.vformmvvm.core

import com.rviannaoliveira.vformmvvm.core.Status.*


/**
 * A generic class that holds a value with its loading resultLoginUser.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(data: T? = null): Resource<T> {
            return Resource(ERROR, data, "No Network")
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}


/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch resultLoginUser.
 */
enum class Status {
    SUCCESS,
    LOADING,
    ERROR,
}

