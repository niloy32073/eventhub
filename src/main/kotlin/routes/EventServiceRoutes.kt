package com.dbytes.routes

import com.dbytes.models.common.Event
import com.dbytes.models.common.Service
import com.dbytes.models.requests.BookingUpdateInfo
import com.dbytes.models.requests.ServiceBookingInfo
import com.dbytes.models.requests.ServiceEventInfo
import com.dbytes.models.requests.ServiceRatingInfo
import com.dbytes.services.EventServiceServices
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.eventServiceRoutes(eventServiceServices: EventServiceServices) {
    routing {
        authenticate("auth-jwt") {
            post("/event") {
                try {
                    val event = call.receive<Event>()
                    eventServiceServices.createEvent(event)
                    call.respond(HttpStatusCode.OK, "Event Created Successful")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Event Creation Failed. Reason: ${e.message}")
                }
            }
            post("/updateEvent") {
                try {
                    val event = call.receive<Event>()
                    eventServiceServices.updateEvent(event)
                    call.respond(HttpStatusCode.OK, "Event Created Successful")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Event Update Failed. Reason: ${e.message}")
                }
            }
            delete("/event/{id}") {
                try {
                    val id =
                        call.pathParameters["id"]?.toLong() ?: return@delete call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                    eventServiceServices.deleteEvent(id)
                    call.respond(HttpStatusCode.OK, "Event Deleted Successful")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Event delete Failed. Reason: ${e.message}")
                }
            }
            get("/eventOfUser/{id}") {
                try {
                    val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                    val events = eventServiceServices.getEventsByUserId(id)
                    call.respond(HttpStatusCode.OK, events)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Event Get Failed. Reason: ${e.message}")
                }
            }
            get("/eventCost/{id}") {
                try {
                    val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                    val cost = eventServiceServices.getEventCost(id)
                    call.respond(HttpStatusCode.OK, cost)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Event Cost Get Failed. Reason: ${e.message}")
                }
            }
            post("/addServicesToEvent") {
                try {
                    val serviceEventInfo = call.receive<ServiceEventInfo>()
                    eventServiceServices.addServiceToEvent(serviceEventInfo)
                    call.respond(HttpStatusCode.OK, "Service Added Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Added Failed. Reason: ${e.message}")
                }
            }
            delete("/removeServicesFromEvent") {
                try {
                    val serviceEventInfo = call.receive<ServiceEventInfo>()
                    eventServiceServices.removeServiceFromEvent(serviceEventInfo)
                    call.respond(HttpStatusCode.OK, "Service Removed Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service removed Failed. Reason: ${e.message}")
                }
            }
            post("/service") {
                try {
                    val service = call.receive<Service>()
                    eventServiceServices.createService(service)
                    call.respond(HttpStatusCode.OK, "Service Created Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Added Failed. Reason: ${e.message}")
                }
            }
            post("/updateService") {
                try {
                    val service = call.receive<Service>()
                    eventServiceServices.updateService(service)
                    call.respond(HttpStatusCode.OK, "Service Updated Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Update Failed. Reason: ${e.message}")
                }
            }
            delete("/service/{id}") {
                try {
                    val id = call.parameters["id"]?.toLong() ?: return@delete call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                    eventServiceServices.deleteServiceById(id)
                    call.respond(HttpStatusCode.OK, "Service Deleted Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Deleted Failed. Reason: ${e.message}")
                }
            }
            get("/services") {
                try {
                    val services = eventServiceServices.getServices()
                    call.respond(HttpStatusCode.OK, services)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Get Failed. Reason: ${e.message}")
                }
            }
            post("/rate") {
                try {
                    val serviceRatingInfo = call.receive<ServiceRatingInfo>()
                    eventServiceServices.rateService(serviceRatingInfo)
                    call.respond(HttpStatusCode.OK, "Service Rating Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Rating Failed. Reason: ${e.message}")
                }
            }
            get("/ratings/{id}") {
                try {
                    val serviceId =
                        call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                    val ratings = eventServiceServices.getServiceRatingsById(serviceId)
                    call.respond(HttpStatusCode.OK, ratings)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Rating Getting Failed. Reason: ${e.message}")
                }
            }
            get("/servicesByProvider/{id}") {
                try {
                    val serviceProviderId =
                        call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                    val services = eventServiceServices.getServiceByProviderId(serviceProviderId)
                    call.respond(HttpStatusCode.OK, services)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Get Failed. Reason: ${e.message}")
                }
            }
            post("/bookService") {
                try {
                    val serviceBookingInfo = call.receive<ServiceBookingInfo>()
                    eventServiceServices.bookService(serviceBookingInfo)
                    call.respond(HttpStatusCode.OK, "Service Booked Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Service Booking Failed. Reason: ${e.message}")
                }
            }
            post("/updateBooking") {
                try {
                    val bookingUpdateInfo = call.receive<BookingUpdateInfo>()
                    eventServiceServices.updateBookingStatus(bookingUpdateInfo)
                    call.respond(HttpStatusCode.OK, "Booking Updated Successfully")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Booking Update Failed. Reason: ${e.message}")
                }
            }
            get("/eventBooking/{id}") {
                try {
                    val eventId = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    val bookings = eventServiceServices.getBookingByEventId(eventId)
                    call.respond(HttpStatusCode.OK, bookings)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Booking Fetch Failed. Reason: ${e.message}")
                }
            }
            get("/serviceProviderBooking/{id}") {
                try {
                    val serviceProviderId =
                        call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest,"Invalid ID")
                    val bookings = eventServiceServices.getBookingByServiceProviderId(serviceProviderId)
                    call.respond(HttpStatusCode.OK, bookings)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Booking Fetch Failed. Reason: ${e.message}")
                }
            }
        }
    }

}