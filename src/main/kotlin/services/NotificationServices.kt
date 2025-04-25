package com.dbytes.services

import com.dbytes.interfaces.NotificationRepositoryInterface
import com.dbytes.models.common.Notification

class NotificationServices(private val notificationRepository: NotificationRepositoryInterface) {
    suspend fun changeNotificationStatus(id:Long) {
        notificationRepository.changeNotificationStatus(id)
    }

    suspend fun getNotificationByReceiverId(receiverId: Long): List<Notification> {
        return notificationRepository.getNotificationByReceiverId(receiverId)
    }
}