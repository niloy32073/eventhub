package com.dbytes.services

import com.dbytes.interfaces.MessageRepositoryInterface
import com.dbytes.models.common.Message
import com.dbytes.models.responses.UserBasicInfo

class MessageServices(private val messageRepository: MessageRepositoryInterface) {
    suspend fun createMessage(message: Message) {
        messageRepository.createMessage(message)
    }

    suspend fun connectedPeople(userId: Long): List<UserBasicInfo>{
        return messageRepository.connectedPeople(userId)
    }

    suspend fun getMessagesById(
        senderId: Long,
        receiverId: Long
    ): List<Message>{
        return messageRepository.getMessagesById(senderId, receiverId)
    }
}