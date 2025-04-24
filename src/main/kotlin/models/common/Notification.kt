package com.dbytes.models.common

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: Long,
    val receiverId: Long,
    val body: String,
    val isRead: Boolean,
    val timestamp: Long
)
