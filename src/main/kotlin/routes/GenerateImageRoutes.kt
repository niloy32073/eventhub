package com.dbytes.routes

import com.dbytes.models.common.ImageGenerateMessage
import com.dbytes.services.ImageGenerateServices
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.imageGenerateMessageRoutes(imageGenerateServices: ImageGenerateServices) {
    routing {
        post("/generateImage") {
            try{
                val imageGenerateMessage = call.receive<ImageGenerateMessage>()
                imageGenerateServices.create(imageGenerateMessage)
                call.respond(HttpStatusCode.OK,"Image Generate successfully")
            } catch(e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "An error occurred")
            }
        }
        get("/generateImageMessages/{id}") {
            try {
                val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                val imageGenerateMessages = imageGenerateServices.getImageGenerateMessageById(id)
                call.respond(HttpStatusCode.OK,imageGenerateMessages)
            } catch(e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "An error occurred")
            }
        }
    }
}