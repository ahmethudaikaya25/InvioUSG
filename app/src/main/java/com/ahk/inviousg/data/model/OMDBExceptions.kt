package com.ahk.inviousg.data.model

sealed class OMDBExceptions : Exception() {
    data class OMDBException(override val message: String) : OMDBExceptions()
    data class MovieNotFoundException(override val message: String) : OMDBExceptions()
    data class OMDBNetworkException(override val message: String) : OMDBExceptions()
    data class OMDBServerException(override val message: String) : OMDBExceptions()
    data class OMDBUnknownException(override val message: String) : OMDBExceptions()

    companion object {
        fun getException(message: String): OMDBExceptions {
            return when {
                message.contains("Movie not found!") -> MovieNotFoundException(message)
                message.contains("Network Error") -> OMDBNetworkException(message)
                message.contains("Server Error") -> OMDBServerException(message)
                else -> OMDBUnknownException(message)
            }
        }
    }
}

