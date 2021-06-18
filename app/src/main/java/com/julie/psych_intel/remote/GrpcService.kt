package com.julie.psych_intel.remote

import android.content.Context
import com.julie.psych_intel.R
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.net.URL

class GrpcService(val context: Context) {

     fun channel(): ManagedChannel {
        val url = URL(context.resources.getString(R.string.server_url))
        val port = if (url.port == -1) url.defaultPort else url.port
        val builder = ManagedChannelBuilder.forAddress(url.host, port)
        if (url.protocol == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }
        return builder.executor(Dispatchers.Default.asExecutor()).build()
    }
}