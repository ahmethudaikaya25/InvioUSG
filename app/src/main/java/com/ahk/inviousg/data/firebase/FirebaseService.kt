package com.ahk.inviousg.data.firebase

import com.ahk.inviousg.data.model.RemoteConfigName
import com.google.android.gms.tasks.Task
import io.reactivex.Single

interface FirebaseService {
    fun fetchRemoteConfigAndActivate(): Task<Boolean>

    fun getStringRemoteConfig(remoteConfigModelsName: RemoteConfigName): Single<String>
}
