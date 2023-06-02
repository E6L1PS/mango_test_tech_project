package com.mango.test_tech_project.util

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()

    data class Loading<out T>(val data: T?) : Resource<T>()

    data class Error<out T>(val message: String, val data: T? = null) : Resource<T>()

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Success(data)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Loading(data)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Error(message, data)
        }
    }
}