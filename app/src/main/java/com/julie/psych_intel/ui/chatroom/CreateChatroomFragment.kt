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
import com.julie.psych_intel.databinding.FragmentCreateChatroomBinding
import com.julie.psych_intel.remote.GrpcService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class CreateChatroomFragment : Fragment() {

    private lateinit var binding: FragmentCreateChatroomBinding

    @Inject
    lateinit var grpcService: GrpcService

    private val chatroom by lazy { ChatroomAPIGrpcKt.ChatroomAPICoroutineStub(grpcService.channel()) }


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