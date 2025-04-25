package com.dbytes.repositories

import com.dbytes.db.ImageGenerateMessageTable
import com.dbytes.interfaces.ImageGenerateRepositoryInterface
import com.dbytes.models.common.ImageGenerateMessage
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class ImageGenerateRepository: ImageGenerateRepositoryInterface {
    override suspend fun create(imageGenerateMessage: ImageGenerateMessage) {
        val uploadsDir = File("uploads")

        // Filter only image files
        val imageFiles = uploadsDir.listFiles { file ->
            file.isFile && file.extension.lowercase() in listOf("jpg", "jpeg", "png", "gif", "webp")
        }

        // Random image selection and link generation
        val randomImageLink = imageFiles?.randomOrNull()?.name?.let { fileName ->
            "http://localhost:8080/uploads/$fileName"
        }
        transaction {
            ImageGenerateMessageTable.insert {
                it[id] = imageGenerateMessage.id
                it[senderId] = imageGenerateMessage.senderId
                it[text] = imageGenerateMessage.text
                it[imageLink] = randomImageLink
            }
        }
    }

    override suspend fun getImageGenerateMessageById(senderId: Long): List<ImageGenerateMessage> = transaction{
        ImageGenerateMessageTable.selectAll().where { ImageGenerateMessageTable.senderId eq senderId }.map {
            ImageGenerateMessage(
                id = it[ImageGenerateMessageTable.id],
                text = it[ImageGenerateMessageTable.text],
                imageLink = it[ImageGenerateMessageTable.imageLink],
                senderId = it[ImageGenerateMessageTable.senderId]
            )
        }
    }
}