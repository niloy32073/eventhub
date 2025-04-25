package com.dbytes.interfaces

import com.dbytes.models.common.ImageGenerateMessage

interface ImageGenerateRepositoryInterface {
    suspend fun create(imageGenerateMessage: ImageGenerateMessage)
    suspend fun getImageGenerateMessageById(senderId: Long): List<ImageGenerateMessage>
}