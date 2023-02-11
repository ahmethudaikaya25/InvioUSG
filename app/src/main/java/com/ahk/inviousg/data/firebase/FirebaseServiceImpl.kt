package com.ahk.inviousg.data.firebase

import com.ahk.inviousg.data.model.RemoteConfigName
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.Single

class FirebaseServiceImpl(
    private val remoteConfig: FirebaseRemoteConfig
) : FirebaseService {
    override fun fetchRemoteConfigAndActivate(): Task<Boolean> = remoteConfig.fetchAndActivate()
    override fun getStringRemoteConfig(remoteConfigModelsName: RemoteConfigName): Single<String> =
        Single.just(remoteConfig.getString(remoteConfigModelsName.name))
}
