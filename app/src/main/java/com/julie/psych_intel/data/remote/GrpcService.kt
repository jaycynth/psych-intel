package com.julie.psych_intel.data.remote

import android.content.Context
import android.widget.Toast.*
import com.julie.psych_intel.R
import com.julie.psych_intel.data.interceptors.NetworkStatusInterceptor
import com.julie.psych_intel.utils.ConnectionManager
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.net.URL
import java.util.logging.Logger

class GrpcService(val context: Context) {

    private val logger = Logger.getLogger(this.javaClass.name)

    fun channel(): ManagedChannel {
        val url = URL(context.resources.getString(R.string.server_url))
        val port = if (url.port == -1) url.defaultPort else url.port

        logger.info("Connecting to ${url.host}:$port")
        makeText(context.applicationContext, "Connecting to ${url.host}:$port", LENGTH_LONG).show()

        val builder = ManagedChannelBuilder.forAddress(url.host, port)
        if (url.protocol == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }
        builder.intercept(NetworkStatusInterceptor(ConnectionManager(context)))
        return builder.executor(Dispatchers.Default.asExecutor()).build()
    }
}