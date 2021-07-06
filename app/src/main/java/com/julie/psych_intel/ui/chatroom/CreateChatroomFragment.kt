package com.julie.psych_intel.ui.chatroom

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.julie.psych_intel.ChatroomAPIGrpcKt
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.R
import com.julie.psych_intel.databinding.FragmentCreateChatroomBinding
import com.julie.psych_intel.databinding.FragmentUserBinding
import com.julie.psych_intel.ui.auth.UserFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.util.logging.Logger

@AndroidEntryPoint
class CreateChatroomFragment : Fragment() {

    private lateinit var binding: FragmentCreateChatroomBinding

    private val logger = Logger.getLogger(this.javaClass.name)

    private fun channel(): ManagedChannel {
        val url = URL(resources.getString(R.string.server_url))
        val port = if (url.port == -1) url.defaultPort else url.port

        logger.info("Connecting to ${url.host}:$port")
        Toast.makeText(requireActivity(), "Connecting to ${url.host}:$port", Toast.LENGTH_LONG)
            .show()

        val builder = ManagedChannelBuilder.forAddress(url.host, port)
        if (url.protocol == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }
        return builder.executor(Dispatchers.Default.asExecutor()).build()
    }

    private val chatroom by lazy { ChatroomAPIGrpcKt.ChatroomAPICoroutineStub(channel()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateChatroomBinding.inflate(inflater, container, false)
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
        Toast.makeText(requireActivity(), channel().getState(true).name, Toast.LENGTH_LONG).show()

        try {
            val chat =
                ChatroomProto.Chatroom.newBuilder().setChatroomId(id).setChatroomName(username)
                    .build()
            val request = ChatroomProto.CreateChatroomRequest.newBuilder().setChatroom(chat).build()
            val response = chatroom.createChatroom(request)

            Toast.makeText(requireActivity(), response.successMessage, Toast.LENGTH_LONG).show()

            requireActivity().runOnUiThread {
                val action = CreateChatroomFragmentDirections.actionCreateChatroomFragmentToChatFragment(id,username)
                findNavController().navigate(action)
            }

        } catch (e: Exception) {
            Toast.makeText(requireActivity(), e.localizedMessage, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}