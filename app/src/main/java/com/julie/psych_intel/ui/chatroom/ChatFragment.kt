package com.julie.psych_intel.ui.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.adapters.ChatMessageAdapter
import com.julie.psych_intel.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chatroomId = ChatFragmentArgs.fromBundle(requireArguments()).chatroomId
        val username = ChatFragmentArgs.fromBundle(requireArguments()).username

        setupRecyclerView()

        val request = ChatroomProto.JoinChatroomRequest.newBuilder()
            .setChatroomId(chatroomId)
            .setUserName(username).build()


        lifecycleScope.launchWhenStarted {
            sendObserveMessageFlow(request)
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
                    sendObserveMessageFlow(sendReq)
                }

            }
        }
    }


    private suspend fun sendObserveMessageFlow(request: ChatroomProto.JoinChatroomRequest) {
        try {
            viewModel.joinChatroom(request).collect {
                println("MYMES" + it.message)
                requireActivity().runOnUiThread {
                    addMessageList(it)
                }
            }
        } catch (e: Throwable) {
            println("Exception from the flow: $e")
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


