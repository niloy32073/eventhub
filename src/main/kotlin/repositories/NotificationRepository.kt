package com.dbytes.repositories

import com.dbytes.db.NotificationsTable
import com.dbytes.interfaces.NotificationRepositoryInterface
import com.dbytes.models.common.Notification
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class NotificationRepository : NotificationRepositoryInterface {
    override suspend fun create(notification: Notification) {
        transaction {
            NotificationsTable.insert {
                it[timestamp] = notification.timestamp
                it[isRead] = notification.isRead
                it[receiverId] = notification.receiverId
                it[body] = notification.body
            }
        }
    }

    override suspend fun changeNotificationStatus(id: Long) {
        transaction {
            NotificationsTable.update({ NotificationsTable.id eq id }) {
                it[NotificationsTable.isRead] = true
            }
        }
    }

    override suspend fun getNotificationByReceiverId(receiverId: Long): List<Notification> = transaction{
        NotificationsTable.selectAll().where { NotificationsTable.receiverId eq receiverId }.map {
            Notification(
                id = it[NotificationsTable.id],
                receiverId = it[NotificationsTable.receiverId],
                body = it[NotificationsTable.body],
                isRead = it[NotificationsTable.isRead],
                timestamp = it[NotificationsTable.timestamp]
            )
        }
    }
}