package com.julie.psych_intel.ui.chatroom

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.repositories.ChatroomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ChatViewModel @ViewModelInject constructor(private val chatroomRepository: ChatroomRepository) :
    ViewModel() {

    private val _events = MutableSharedFlow<ChatroomProto.JoinChatroomRequest>()
    val events = _events.asSharedFlow()


    suspend fun postEvent(event: ChatroomProto.JoinChatroomRequest) {
        _events.emit(event)
    }


    fun joinChatroom(request: ChatroomProto.JoinChatroomRequest): Flow<ChatroomProto.ChatMessage> =
            chatroomRepository.joinChatroom(request)



    fun createChatroom(request:ChatroomProto.CreateChatroomRequest) =
        chatroomRepository.createChatroom(request)


    fun getChatrooms(): LiveData<List<ChatroomProto.Chatroom>> =
        chatroomRepository.getChatrooms()

}