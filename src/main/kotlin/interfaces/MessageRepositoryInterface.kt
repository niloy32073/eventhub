package com.dbytes.interfaces

import com.dbytes.models.common.Message
import com.dbytes.models.responses.UserBasicInfo

interface MessageRepositoryInterface {
    suspend fun createMessage(message : Message)
    suspend fun connectedPeople(userId : Long): List<UserBasicInfo>
    suspend fun getMessagesById(senderId : Long, receiverId: Long) : List<Message>
}