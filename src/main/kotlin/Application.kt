package com.dbytes

import com.dbytes.repositories.AuthRepository
import com.dbytes.repositories.EventRepository
import com.dbytes.repositories.ImageGenerateRepository
import com.dbytes.repositories.MessageRepository
import com.dbytes.repositories.NotificationRepository
import com.dbytes.repositories.ServiceRepository
import com.dbytes.repositories.UserRepository
import com.dbytes.routes.authRoutes
import com.dbytes.routes.eventServiceRoutes
import com.dbytes.routes.imageGenerateMessageRoutes
import com.dbytes.routes.imageUploadRoutes
import com.dbytes.routes.messageRoutes
import com.dbytes.routes.notificationRoutes
import com.dbytes.routes.userRoutes
import com.dbytes.services.AuthServices
import com.dbytes.services.EventServiceServices
import com.dbytes.services.ImageGenerateServices
import com.dbytes.services.MessageServices
import com.dbytes.services.NotificationServices
import com.dbytes.services.UserServices
import com.dbytes.utils.DBConfig
import com.dbytes.utils.JWTConfig
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.DatabaseConfig

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(Authentication){
        jwt("auth-jwt"){
            realm = "Access token"
            verifier(JWTConfig.verifyToken())
            validate { credential ->
                if (credential.payload.getClaim("userId").asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
    DBConfig.connect()
    val authRepository: AuthRepository = AuthRepository()
    val userRepository: UserRepository = UserRepository()
    val eventRepository: EventRepository = EventRepository()
    val serviceRepository: ServiceRepository = ServiceRepository()
    val notificationRepository: NotificationRepository = NotificationRepository()
    val messageRepository: MessageRepository = MessageRepository()
    val imageGenerateRepository: ImageGenerateRepository = ImageGenerateRepository()

    val authServices = AuthServices(authRepository)
    val userServices = UserServices(userRepository)
    val eventServiceServices = EventServiceServices(eventRepository,serviceRepository,notificationRepository)
    val notificationServices = NotificationServices(notificationRepository)
    val messageServices = MessageServices(messageRepository)
    val imageGenerateServices = ImageGenerateServices(imageGenerateRepository)

    authRoutes(authServices)
    userRoutes(userServices)
    eventServiceRoutes(eventServiceServices)
    notificationRoutes(notificationServices)
    messageRoutes(messageServices)
    imageUploadRoutes()
    imageGenerateMessageRoutes(imageGenerateServices)

}
