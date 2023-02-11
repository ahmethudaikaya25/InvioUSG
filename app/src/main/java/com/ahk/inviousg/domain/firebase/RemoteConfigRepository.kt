package com.ahk.inviousg.domain.firebase

import com.ahk.inviousg.data.firebase.FirebaseService
import com.ahk.inviousg.data.model.RemoteConfigName

class RemoteConfigRepository(private val firebaseService: FirebaseService) {
    fun fetchRemoteConfig() =
        mapTaskToFirebaseResponse(firebaseService.fetchRemoteConfigAndActivate())

    fun getStringRemoteConfig(remoteConfigModelsName: RemoteConfigName) =
        firebaseService.getStringRemoteConfig(remoteConfigModelsName)
}
