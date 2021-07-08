package com.julie.psych_intel.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.julie.psych_intel.ChatroomAPIGrpcKt
import com.julie.psych_intel.ChatroomProto
import com.julie.psych_intel.remote.GrpcService
import com.julie.psych_intel.ui.chatroom.CreateChatroomFragmentDirections
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

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


    fun getChatrooms() = liveData {
        try {
            val request = ChatroomProto.ListChatroomRequest.newBuilder().build()
            val response = chatroom.listChatrooms(request)
            emit(response.chatroomListList)
        } catch (e: Exception) {
            e.message?.let { Log.d("RESPONSE", it) }
        }
    }


    fun joinChatroom(req: ChatroomProto.JoinChatroomRequest) = flow {
        try {
            val response = chatroom.joinChatroom(flowOf(req))
            response.collect {
                emit(it)
            }

        } catch (e: Exception) {
            e.message?.let { Log.d("RESPONSE", it) }
        }
    }

}