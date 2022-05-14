package com.julie.psych_intel.ui.chatroom

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.databinding.FragmentCreateChatroomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateChatroomFragment : Fragment() {

    private lateinit var binding: FragmentCreateChatroomBinding

    private val viewModel: ChatViewModel by viewModels()

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

    private fun sendReq(id: String, username: String) {

        val chat =
            ChatroomProto.Chatroom.newBuilder().setChatroomId(id).setChatroomName(username)
                .build()

        val request = ChatroomProto.CreateChatroomRequest.newBuilder().setChatroom(chat).build()

        viewModel.createChatroom(request).observe(viewLifecycleOwner, Observer {
            if (it.chatroom != null) {
                Toast.makeText(requireActivity(), it.successMessage, Toast.LENGTH_LONG).show()

                val action =
                    CreateChatroomFragmentDirections.actionCreateChatroomFragmentToUserFragment()
                        .setChatroomId(it.chatroom.chatroomId)
                findNavController().navigate(action)
            }

        })


    }
}