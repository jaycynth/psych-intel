package com.julie.psych_intel.ui.chatroom

import androidx.lifecycle.ViewModel
import com.julie.psych_intel.ChatroomProto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ChatViewModel : ViewModel() {

    private val _events = MutableSharedFlow<ChatroomProto.JoinChatroomRequest>()
    val events = _events.asSharedFlow() // read-only public view

    suspend fun postEvent(event: ChatroomProto.JoinChatroomRequest) {
        _events.emit(event) // suspends until subscribers receive it
    }
}