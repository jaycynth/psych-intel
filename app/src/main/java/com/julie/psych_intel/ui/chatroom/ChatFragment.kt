package com.julie.psych_intel.ui.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.julie.psych_intel.ChatroomAPIGrpcKt
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.adapters.ChatMessageAdapter
import com.julie.psych_intel.databinding.FragmentChatBinding
import com.julie.psych_intel.remote.GrpcService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    @Inject
    lateinit var grpcService:GrpcService

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

    private val chatroom by lazy { ChatroomAPIGrpcKt.ChatroomAPICoroutineStub(grpcService.channel()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chatroomId = ChatFragmentArgs.fromBundle(requireArguments()).chatroomId
        val username = ChatFragmentArgs.fromBundle(requireArguments()).username

        setupRecyclerView()

        val request = ChatroomProto.JoinChatroomRequest.newBuilder()
            .setChatroomId(chatroomId)
            .setUserName(username).build()


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
                    .setChatroomId(chatroomId)
                    .setMessage(msg)
                    .setUserName(username).build()

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


