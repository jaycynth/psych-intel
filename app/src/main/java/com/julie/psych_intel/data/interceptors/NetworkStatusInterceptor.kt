package com.julie.psych_intel.data.interceptors

import com.julie.psych_intel.data.models.NetworkUnavailable
import com.julie.psych_intel.utils.ConnectionManager
import io.grpc.*


class NetworkStatusInterceptor(private val connectionManager: ConnectionManager) :
    ClientInterceptor {

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        method: MethodDescriptor<ReqT, RespT>?,
        callOptions: CallOptions?,
        next: Channel
    ): ClientCall<ReqT, RespT> {
        return if (connectionManager.isConnected) {
            next.newCall(method, callOptions)
        } else {
            throw NetworkUnavailable()
        }
    }
}