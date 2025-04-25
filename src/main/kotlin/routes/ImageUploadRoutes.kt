package com.dbytes.routes


import com.dbytes.models.responses.ImageUploadResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.application.Application
import io.ktor.server.http.content.files
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticFiles
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.io.File
import java.util.UUID

fun Application.imageUploadRoutes(){
    routing {
        post("/upload") {
            val multipart = call.receiveMultipart()
            var imageUrl: String? = null

            multipart.forEachPart { part ->
                if (part is PartData.FileItem) {
                    val extension = File(part.originalFileName ?: "temp").extension
                    val fileName = "${UUID.randomUUID()}.$extension"
                    val uploadDir = File("uploads")
                    if (!uploadDir.exists()) uploadDir.mkdirs()
                    val file = File(uploadDir, fileName)

                    part.streamProvider().use { input ->
                        file.outputStream().buffered().use { output ->
                            input.copyTo(output)
                        }
                    }

                    imageUrl = "http://localhost:8080/uploads/$fileName"
                }
                part.dispose()
            }

            if (imageUrl != null) {
                call.respond(HttpStatusCode.OK,ImageUploadResponse(imageLink = imageUrl!!))
            } else {
                call.respond(HttpStatusCode.BadRequest, "No image uploaded")
            }
        }
        staticFiles("/uploads", File("uploads"))
    }
}