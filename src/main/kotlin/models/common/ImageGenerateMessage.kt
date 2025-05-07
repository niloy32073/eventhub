package com.dbytes.models.common

import kotlinx.serialization.Serializable

@Serializable
data class ImageGenerateMessage(
    val id: Long,
    val senderId: Long,
    val text: String,
    val imageLink: String?
)
