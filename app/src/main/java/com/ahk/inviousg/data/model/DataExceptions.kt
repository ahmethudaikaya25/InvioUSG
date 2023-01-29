package com.ahk.inviousg.data.model

sealed class DataExceptions : Exception() {
    data class DataException(override val message: String) : DataExceptions()
    data class MovieNotFoundException(override val message: String) : DataExceptions()
    data class DataNetworkException(override val message: String) : DataExceptions()
    data class DataServerException(override val message: String) : DataExceptions()
    data class DataUnknownException(override val message: String) : DataExceptions()

    companion object {
        fun getException(message: String): DataExceptions {
            return when {
                message.contains("Movie not found!") -> MovieNotFoundException(message)
                message.contains("Network Error") -> DataNetworkException(message)
                message.contains("Server Error") -> DataServerException(message)
                else -> DataUnknownException(message)
            }
        }
    }
}

