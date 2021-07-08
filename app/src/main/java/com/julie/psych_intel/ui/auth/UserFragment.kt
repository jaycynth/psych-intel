package com.julie.psych_intel.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.julie.psych_intel.R
import com.julie.psych_intel.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatroomId =  UserFragmentArgs.fromBundle(requireArguments()).chatroomId

        if(chatroomId != "null"){
            binding.chatroomId.setText(chatroomId)
        }

        binding.viewChatroomsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_chatroomListFragment)
        }

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
                    val action =
                        UserFragmentDirections.actionUserFragmentToChatFragment(id, username)
                    findNavController().navigate(action)
                }
            }
        }
    }


}