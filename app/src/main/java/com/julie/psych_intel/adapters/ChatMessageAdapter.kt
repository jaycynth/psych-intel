package com.julie.psych_intel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.databinding.ChatMessageItemBinding

class ChatMessageAdapter :
    RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder>() {

    private val items = ArrayList<ChatroomProto.ChatMessage>()

    fun setItems(items: ArrayList<ChatroomProto.ChatMessage>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatMessageItemBinding.inflate(inflater, parent, false)
        return ChatMessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) =
        holder.bind(items[position])


    override fun getItemCount(): Int = items.size

    inner class ChatMessageViewHolder(private val binding: ChatMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatroomProto.ChatMessage) {
            binding.chatMessage = item
            binding.executePendingBindings()
        }

    }
}