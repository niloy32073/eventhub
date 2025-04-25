package com.dbytes.routes

import com.dbytes.models.requests.ChangeUserInfo
import com.dbytes.models.requests.ChangePasswordInfo
import com.dbytes.services.UserServices
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Application.userRoutes(userServices: UserServices) {
    routing {
        authenticate("auth-jwt") {
            get("/user/{id}") {
                val userId = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                try {
                    val user = userServices.getUserById(userId)
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "User not found")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Failed due to ${e.message}")
                }
            }
            post("/changePassword") {
                try {
                    val changePasswordInfo = call.receive<ChangePasswordInfo>()
                    val isMatched =
                        userServices.checkUserPassword(changePasswordInfo.userId, changePasswordInfo.oldPassword)
                    if (isMatched) {
                        userServices.updateUserPassword(changePasswordInfo.userId, changePasswordInfo.newPassword)
                        call.respond(HttpStatusCode.OK, "Change Password Successfully")
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Failed due to WrongPassword")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Failed due to ${e.message}")
                }
            }
            delete("/user/{id}") {
                val userId = call.parameters["id"]?.toLong() ?: return@delete call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                try {
                    userServices.deleteUserById(userId!!)
                    call.respond(HttpStatusCode.OK, "User Deleted Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Failed due to ${e.message}")
                }
            }
            post("/changeUserInfo") {
                try {
                    val changeUserInfo = call.receive<ChangeUserInfo>()
                    userServices.updateUserNameById(changeUserInfo.id, changeUserInfo.name)
                    call.respond(HttpStatusCode.OK, "User Updated Successfully")
                    userServices.updateUserPhoneById(changeUserInfo.id, changeUserInfo.phone)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Failed due to ${e.message}")
                }
            }
        }
    }
}