package com.julie.psych_intel.ui.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.adapters.ChatroomsAdapter
import com.julie.psych_intel.databinding.FragmentChatroomListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatroomListFragment : Fragment() {

    private lateinit var binding: FragmentChatroomListBinding
    private lateinit var adapter: ChatroomsAdapter

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatroomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        val request = ChatroomProto.ListChatroomRequest.newBuilder().build()

        viewModel.getChatrooms(request).observe(viewLifecycleOwner, Observer{
            adapter.setItems(it)
        })
    }


    private fun setupRecyclerview() {
        adapter = ChatroomsAdapter()
        binding.chatroomsRv.adapter = adapter
    }
}