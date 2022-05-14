package com.julie.psych_intel.repositories

import android.util.Log
import androidx.lifecycle.liveData
import com.julie.psych_intel.ChatroomAPIGrpcKt
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.data.remote.GrpcService
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ChatroomRepository @Inject constructor(
    val grpcService: GrpcService
) {

    private val chatroom by lazy { ChatroomAPIGrpcKt.ChatroomAPICoroutineStub(grpcService.channel()) }


    fun createChatroom(req: ChatroomProto.CreateChatroomRequest) = liveData {
        try {
            val response = chatroom.createChatroom(req)
            emit(response)
        } catch (e: Exception) {
            e.message?.let { Log.d("RESPONSE", it) }
        }
    }


    fun getChatrooms(request: ChatroomProto.ListChatroomRequest) = liveData {
        try {
            val response = chatroom.listChatrooms(request)
            emit(response.chatroomListList)
        } catch (e: Exception) {
            e.message?.let { Log.d("RESPONSE", it) }
        }
    }


    fun joinChatroom(req: SharedFlow<ChatroomProto.JoinChatroomRequest>) : Flow<ChatroomProto.ChatMessage> {
        try {
            return chatroom.joinChatroom(req)
        } catch (e: Exception) {
            e.message?.let { Log.d("RESPONSE", it) }
        }
        return emptyFlow()
    }

}