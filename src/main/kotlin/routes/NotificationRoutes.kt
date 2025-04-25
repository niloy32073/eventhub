package com.dbytes.routes

import com.dbytes.services.NotificationServices
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.notificationRoutes(notificationServices: NotificationServices) {
    routing {
        authenticate("auth-jwt") {
            post("/notificationStatusUpdate/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid request.")
                try{
                    notificationServices.changeNotificationStatus(id)
                    call.respond(HttpStatusCode.OK,"Notification status changed successfully")
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Notification Update Failed. Reason: ${ex.message}")
                }
            }
            get("/notifications/{id}"){
                val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid request.")
                try{
                    val notifications = notificationServices.getNotificationByReceiverId(receiverId = id)
                    call.respond(HttpStatusCode.OK,notifications)
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Notification Update Failed. Reason: ${ex.message}")
                }
            }
        }
    }
}