package com.julie.psych_intel

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.julie.psych_intel.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.util.logging.Logger

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    private val logger = Logger.getLogger(this.javaClass.name)

    private fun channel(): ManagedChannel {
        val url = URL(resources.getString(R.string.server_url))
        val port = if (url.port == -1) url.defaultPort else url.port

        logger.info("Connecting to ${url.host}:$port")

        val builder = ManagedChannelBuilder.forAddress(url.host, port)
        if (url.protocol == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }

        return builder.executor(Dispatchers.Default.asExecutor()).build()
    }

    // lazy otherwise resources is null
    private val chatroom by lazy { ChatroomAPIGrpcKt.ChatroomAPICoroutineStub(channel()) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitBtn.setOnClickListener {
            val id = binding.chatroomId.text.toString().trim()
            val username = binding.chatroomUsername.text.toString().trim()

            when {
                TextUtils.isEmpty(id) -> {
                    binding.chatroomId.error = "Enter chatroom id"
                }
                TextUtils.isEmpty(username) -> {
                    binding.chatroomUsername.error = "Enter chatroom username"
                }
                else -> {
                    sendReq(id, username)
                }
            }
        }
    }

    private fun sendReq(id: String, username: String) = runBlocking {
        try {
            val request = JoinChatroomRequest.newBuilder().setChatroomId(id)
                .setUserName(username)
                .build()
//            val response = chatroom.joinChatroom(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}