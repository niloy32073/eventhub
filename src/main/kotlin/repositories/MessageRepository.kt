package com.dbytes.repositories


import com.dbytes.db.MessageTable
import com.dbytes.db.UserTable
import com.dbytes.interfaces.MessageRepositoryInterface
import com.dbytes.models.common.Message
import com.dbytes.models.responses.UserBasicInfo
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class MessageRepository: MessageRepositoryInterface {
    override suspend fun createMessage(message: Message) {
        transaction {
            MessageTable.insert {
                it[senderId] = message.senderId
                it[receiverId] = message.receiverId
                it[text] = message.text
                it[MessageTable.ImageLink] = message.imageLink
                it[sentAt] = message.sentAt
            }
        }
    }

    override suspend fun connectedPeople(userId: Long): List<UserBasicInfo> = transaction {
        val senderIds = MessageTable.select(MessageTable.senderId).where{ MessageTable.receiverId eq userId }.map{it[MessageTable.senderId]}
        val receiverIds = MessageTable.select(MessageTable.receiverId).where{ MessageTable.senderId eq userId }.map{it[MessageTable.receiverId]}
        val connectedUserIds =(senderIds + receiverIds).filter { it != userId }.toSet()
        UserTable.selectAll().where{ UserTable.id inList connectedUserIds.toList() }.map {
            UserBasicInfo(userId = it[UserTable.id], name = it[UserTable.name])
        }
    }

    override suspend fun getMessagesById(
        senderId: Long,
        receiverId: Long
    ): List<Message> = transaction {
        MessageTable.selectAll().where((MessageTable.senderId eq senderId) and (MessageTable.receiverId eq receiverId)).map{
            Message(
                id = it[MessageTable.id],
                senderId = it[MessageTable.senderId],
                receiverId = it[MessageTable.receiverId],
                text = it[MessageTable.text],
                imageLink = it[MessageTable.ImageLink],
                sentAt = it[MessageTable.sentAt],
            )
        }
    }
}