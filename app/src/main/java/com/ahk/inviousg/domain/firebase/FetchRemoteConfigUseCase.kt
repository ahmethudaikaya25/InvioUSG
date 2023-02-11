package com.ahk.inviousg.domain.firebase

import com.ahk.inviousg.data.model.RemoteConfigName
import com.ahk.inviousg.domain.internetstate.InternetStateRepository
import com.ahk.inviousg.util.API_KEY
import com.ahk.inviousg.util.BASE_URL
import com.ahk.inviousg.util.DEFAULT_API_ADDRESS
import io.reactivex.Completable
import io.reactivex.Single

class FetchRemoteConfigUseCase(
    private val internetStateRepository: InternetStateRepository,
    private val remoteConfigRepository: RemoteConfigRepository,
) {
    fun invoke(): Completable {
        return if (BASE_URL == DEFAULT_API_ADDRESS || API_KEY.isEmpty() || BASE_URL.isEmpty()) {
            internetStateRepository.isInternetAvailable()
                .andThen(remoteConfigRepository.fetchRemoteConfig())
                .toSingleDefault(Any())
                .flatMap {
                    remoteConfigRepository.getStringRemoteConfig(RemoteConfigName.API_KEY)
                }
                .flatMap {
                    API_KEY = it
                    remoteConfigRepository.getStringRemoteConfig(RemoteConfigName.API_BASE_URL)
                }
                .flatMap {
                    BASE_URL = it
                    Single.just(Any())
                }
                .ignoreElement()
        } else {
            internetStateRepository.isInternetAvailable()
        }
    }
}
