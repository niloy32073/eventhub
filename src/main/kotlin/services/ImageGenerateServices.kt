package com.dbytes.services

import com.dbytes.interfaces.ImageGenerateRepositoryInterface
import com.dbytes.models.common.ImageGenerateMessage


class ImageGenerateServices(private val imageGenerateRepository: ImageGenerateRepositoryInterface) {
    suspend fun create(imageGenerateMessage: ImageGenerateMessage) {
        imageGenerateRepository.create(imageGenerateMessage)
    }
    suspend fun getImageGenerateMessageById(senderId: Long): List<ImageGenerateMessage> {
        return imageGenerateRepository.getImageGenerateMessageById(senderId)
    }
}