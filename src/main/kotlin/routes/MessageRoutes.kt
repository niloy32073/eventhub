package com.dbytes.routes

import com.dbytes.models.common.Message
import com.dbytes.models.requests.MessageRequestInfo
import com.dbytes.services.MessageServices
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.messageRoutes(messageServices: MessageServices) {
    routing {
        authenticate("auth-jwt") {
            post("/sendMessage") {
                try {
                    val message = call.receive<Message>()
                    messageServices.createMessage(message)
                    call.respond(HttpStatusCode.OK,"Message Sent")
                } catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Message Sent Failed. Reason: ${e.message}")
                }
            }

            get("/getConnectedPeople/{id}"){
                try {
                    val userId = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                    val connectedPeople = messageServices.connectedPeople(userId)
                    call.respond(HttpStatusCode.OK, connectedPeople)
                } catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Fetching Failed. Reason: ${e.message}")
                }
            }

            post("/messages"){
                try {
                    val messageRequestInfo = call.receive<MessageRequestInfo>()
                    val messages = messageServices.getMessagesById(senderId = messageRequestInfo.senderId, receiverId = messageRequestInfo.receiverId)
                    call.respond(HttpStatusCode.OK,messages)
                } catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Fetching Failed. Reason: ${e.message}")
                }
            }
        }
    }
}