package com.ahk.inviousg.domain.internetstate // ktlint-disable filename

import com.ahk.inviousg.data.model.DataExceptions
import io.reactivex.Completable

fun mapInternetState(state: Boolean): Completable {
    return if (state) {
        Completable.complete()
    } else {
        Completable.error(DataExceptions.InternetNotAvailableException("Internet not available"))
    }
}
