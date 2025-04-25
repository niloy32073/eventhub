package com.dbytes.models.common

data class ImageGenerateMessage(
    val id: Long,
    val senderId: Long,
    val text: String,
    val imageLink: String?
)
