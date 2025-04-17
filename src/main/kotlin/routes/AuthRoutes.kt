package com.dbytes.routes

import com.dbytes.models.common.User
import com.dbytes.models.requests.UserSignInInfo
import com.dbytes.models.responses.UserLogInResult
import com.dbytes.services.AuthServices
import com.dbytes.utils.JWTConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Application.authRoutes(authServices: AuthServices) {

    routing {
        post("/signup"){
            try {
                val request = call.receive<User>()
                val user = authServices.registerUser(request)
                call.respond(HttpStatusCode.Created, user)
            }catch(e:IllegalArgumentException){
                call.respond(HttpStatusCode.BadRequest,e.message ?: "Invalid request.")
            }
        }

        post("/signin") {
            try{
                val request = call.receive<UserSignInInfo>()
                val user = withContext(Dispatchers.IO) {
                    authServices.loginUser(request)
                }
                val token = JWTConfig.generateToken(user.id.toString())
                var id = 0L
                if(user.id != null)
                    id = user.id
                val userLogInResult = UserLogInResult(
                    userId = id,
                    roles = user.role.toString(),
                    token = token
                )
                call.respond(HttpStatusCode.OK,userLogInResult)
            }catch(e:IllegalArgumentException){
                call.respond(HttpStatusCode.BadRequest,e.message ?: "Invalid request.")
            }
        }
    }
}