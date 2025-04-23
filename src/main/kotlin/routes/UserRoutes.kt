package com.dbytes.routes

import com.dbytes.models.requests.ChangeNameInfo
import com.dbytes.models.requests.ChangePasswordInfo
import com.dbytes.models.requests.ChangePhoneInfo
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
                val userId = call.parameters["id"]?.toLong()
                try {
                    if (userId != null) {
                        val user = userServices.getUserById(userId)
                        if (user != null) {
                            call.respond(HttpStatusCode.OK, user)
                        } else {
                            call.respond(HttpStatusCode.BadRequest, "User not found")
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "UserId not found")
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
                val userId = call.parameters["id"]?.toLong()
                try {
                    userServices.deleteUserById(userId!!)
                    call.respond(HttpStatusCode.OK, "User Deleted Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Failed due to ${e.message}")
                }
            }
            post("/changeName") {
                try {
                    val changeName = call.receive<ChangeNameInfo>()
                    userServices.updateUserNameById(changeName.id, changeName.name)
                    call.respond(HttpStatusCode.OK, "User Updated Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Failed due to ${e.message}")
                }
            }
            post("/changePhone") {
                try {
                    val changePhoneInfo = call.receive<ChangePhoneInfo>()
                    userServices.updateUserPhoneById(changePhoneInfo.id, changePhoneInfo.phone)
                    call.respond(HttpStatusCode.OK, "User Updated Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Failed due to ${e.message}")
                }
            }
        }
    }
}