package com.julie.psych_intel.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.julie.psych_intel.*
import com.julie.psych_intel.adapters.ChatMessageAdapter
import com.julie.psych_intel.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.net.URL

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var adapter: ChatMessageAdapter
    private val viewModel: ChatViewModel by viewModels()

    private val chatMessageList: ArrayList<ChatroomProto.ChatMessage> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun channel(): ManagedChannel {
        val url = URL(resources.getString(R.string.server_url))
        val port = if (url.port == -1) url.defaultPort else url.port

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()


        adapter.setItems(chatMessageList)


        val request = ChatroomProto.JoinChatroomRequest.newBuilder()
            .setChatroomId("5")
            .setUserName("Cynth").build()


        lifecycleScope.launchWhenStarted {

            viewModel.postEvent(request)

            try {
                chatroom.joinChatroom(viewModel.events).collect {
                    println("MYMES" + it.message)
                    requireActivity().runOnUiThread {
                        addMessageList(it)
                    }
                }
            } catch (e: Throwable) {
                println("Exception from the flow: $e")
            }
        }


        binding.sendBtn.setOnClickListener {
            val msg: String = binding.message.text.toString().trim()
            if (msg.isNotEmpty()) {
                binding.message.setText("")


                val sendReq = ChatroomProto.JoinChatroomRequest.newBuilder()
                    .setChatroomId("5")
                    .setMessage(msg)
                    .setUserName("Cynth").build()

                lifecycleScope.launchWhenStarted {
                    viewModel.postEvent(sendReq)
                }

            }
        }
    }


    private fun addMessageList(chatMessage: ChatroomProto.ChatMessage) {
        chatMessageList.add(chatMessage)
        adapter.setItems(chatMessageList)
        adapter.notifyDataSetChanged()
    }


    private fun setupRecyclerView() {
        adapter = ChatMessageAdapter()
        binding.messagesList.adapter = adapter
    }
}


