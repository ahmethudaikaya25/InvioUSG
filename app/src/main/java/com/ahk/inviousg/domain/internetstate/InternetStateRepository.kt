package com.ahk.inviousg.domain.internetstate

import com.ahk.inviousg.data.internetstate.InternetStateProvider
import io.reactivex.Completable

class InternetStateRepository(
    private val internetStateProvider: InternetStateProvider
) {
    fun isInternetAvailable(): Completable {
        return mapInternetState(internetStateProvider.check())
    }
}
