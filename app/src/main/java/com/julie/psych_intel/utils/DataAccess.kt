package com.julie.psych_intel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers


fun <T> getData(grpcNetworkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val responseStatus = grpcNetworkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(Resource.success(responseStatus.data!!))
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }

    }