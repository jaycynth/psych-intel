package com.julie.psych_intel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.databinding.ChatroomItemBinding

class ChatroomsAdapter : RecyclerView.Adapter<ChatroomsAdapter.ChatroomsViewHolder>() {

    private val items = ArrayList<ChatroomProto.Chatroom>()

    fun setItems(items: List<ChatroomProto.Chatroom>) {
        this.items.clear()
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatroomsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatroomItemBinding.inflate(inflater, parent, false)
        return ChatroomsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatroomsViewHolder, position: Int) =
        holder.bind(items[position])


    override fun getItemCount(): Int = items.size

    inner class ChatroomsViewHolder(private val binding: ChatroomItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatroom: ChatroomProto.Chatroom) {
            binding.chatroom = chatroom
            binding.executePendingBindings()
        }
    }

}

