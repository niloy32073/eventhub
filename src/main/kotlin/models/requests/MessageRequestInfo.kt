package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class MessageRequestInfo(
    val receiverId: Long,
    val senderId: Long,
)
