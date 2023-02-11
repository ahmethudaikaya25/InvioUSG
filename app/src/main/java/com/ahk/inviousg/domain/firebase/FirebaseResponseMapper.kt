package com.ahk.inviousg.domain.firebase

import com.ahk.inviousg.data.model.DataExceptions
import com.google.android.gms.tasks.Task
import io.reactivex.Completable

fun mapTaskToFirebaseResponse(task: Task<Boolean>): Completable {
    return Completable.create { emitter ->
        task.addOnCompleteListener {
            if (it.isSuccessful) {
                emitter.onComplete()
            } else {
                emitter.onError(DataExceptions.ParametersAreDownloadingException("Parameters couldn't downloaded"))
            }
        }.addOnCanceledListener {
            emitter.onError(DataExceptions.ParametersAreDownloadingException("Parameter downloading cancelled"))
        }
    }
}
