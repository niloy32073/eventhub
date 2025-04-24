package com.dbytes.interfaces

import com.dbytes.models.common.Notification

interface NotificationRepositoryInterface {
    suspend fun create(notification: Notification)
    suspend fun changeNotificationStatus(id:Long)
    suspend fun getNotificationByReceiverId(receiverId: Long): List<Notification>
}