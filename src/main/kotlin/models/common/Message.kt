package com.dbytes.models.common

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: Long = 0,
    val senderId: Long,
    val receiverId: Long,
    val text: String,
    val imageLink: String? = null,
    val sentAt: Long
)

