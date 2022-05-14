package com.julie.psych_intel.ui.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.repositories.ChatroomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatroomRepository: ChatroomRepository) :
    ViewModel() {

    private val _events = MutableSharedFlow<ChatroomProto.JoinChatroomRequest>()
    val events = _events.asSharedFlow()


    fun postEvent(event: ChatroomProto.JoinChatroomRequest) = viewModelScope.launch {
        _events.emit(event)
    }


    fun joinChatroom(request: SharedFlow<ChatroomProto.JoinChatroomRequest>): Flow<ChatroomProto.ChatMessage> =
        chatroomRepository.joinChatroom(request)


    fun createChatroom(request: ChatroomProto.CreateChatroomRequest) =
        chatroomRepository.createChatroom(request)


    fun getChatrooms(request: ChatroomProto.ListChatroomRequest): LiveData<List<ChatroomProto.Chatroom>> =
        chatroomRepository.getChatrooms(request)

}