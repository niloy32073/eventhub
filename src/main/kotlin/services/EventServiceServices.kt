package com.dbytes.services

import com.dbytes.enums.BookingStatus
import com.dbytes.interfaces.EventRepositoryInterface
import com.dbytes.interfaces.NotificationRepositoryInterface
import com.dbytes.interfaces.ServiceRepositoryInterface
import com.dbytes.models.common.Event
import com.dbytes.models.common.Notification
import com.dbytes.models.common.Service
import com.dbytes.models.requests.BookingUpdateInfo
import com.dbytes.models.requests.ServiceBookingInfo
import com.dbytes.models.requests.ServiceEventInfo
import com.dbytes.models.requests.ServiceRatingInfo
import com.dbytes.models.responses.BookingWithServiceDetails
import java.time.LocalDateTime

class EventServiceServices (private val eventRepository: EventRepositoryInterface, private val serviceRepository: ServiceRepositoryInterface, private val notificationRepository: NotificationRepositoryInterface) {
    suspend fun createEvent(event: Event){
        eventRepository.createEvent(event)
        notificationRepository.create(Notification(
            id = 0L,
            receiverId = event.organizerId,
            body = "${event.name} is created",
            isRead = false,
            timestamp = event.date,
        ))
    }

    suspend fun updateEvent(event: Event):Boolean{
        val isUpdated = eventRepository.updateEvent(event)
        if(isUpdated){
            notificationRepository.create(Notification(
                id = 0L,
                receiverId = event.organizerId,
                body = "${event.name} is updated",
                isRead = false,
                timestamp = event.date,
            ))
        }
        else{
            notificationRepository.create(Notification(
                id = 0L,
                receiverId = event.organizerId,
                body = "${event.name} is not updated",
                isRead = false,
                timestamp = event.date,
            ))
        }
        return eventRepository.updateEvent(event)

    }

    suspend fun deleteEvent(id:Long):Boolean{
        val event = eventRepository.getEvent(id)
        if(event != null) {
            notificationRepository.create(
                Notification(
                    id = 0,
                    receiverId = event.organizerId,
                    body = "${event.name} is deleted",
                    isRead = false,
                    timestamp = System.currentTimeMillis(),
                )
            )
        }
        return eventRepository.deleteEvent(id)
    }

    suspend fun getEventsByUserId(id:Long):List<Event>{
        return eventRepository.getEventsByUserId(id)
    }

    suspend fun getEventCost(id:Long):Float{
        return eventRepository.getEventCost(id)
    }

    suspend fun addServiceToEvent(serviceEventInfo: ServiceEventInfo){
        val event = eventRepository.getEvent(serviceEventInfo.eventId)
        val service = serviceRepository.getService(serviceEventInfo.serviceId)
        if(event != null && service!=null) {
            notificationRepository.create(
                Notification(
                    id = 0,
                    receiverId = event.organizerId,
                    body = "${service.title} is Added to ${event.organizerId}",
                    isRead = false,
                    timestamp = System.currentTimeMillis(),
                )
            )
        }
        eventRepository.addServicesToEvent(eventId = serviceEventInfo.eventId,serviceId = serviceEventInfo.serviceId)
    }
    suspend fun removeServiceFromEvent(serviceEventInfo: ServiceEventInfo){
        val event = eventRepository.getEvent(serviceEventInfo.eventId)
        val service = serviceRepository.getService(serviceEventInfo.serviceId)
        if(event != null && service!=null) {
            notificationRepository.create(
                Notification(
                    id = 0,
                    receiverId = event.organizerId,
                    body = "${service.title} is Removed from ${event.organizerId}",
                    isRead = false,
                    timestamp = System.currentTimeMillis(),
                )
            )
        }
        eventRepository.removeServicesFromEvent(eventId = serviceEventInfo.eventId,serviceId = serviceEventInfo.serviceId)
    }

    suspend fun createService(service: Service){
        notificationRepository.create(
            Notification(
                id = 0,
                receiverId = service.serviceProviderId,
                body = "${service.title} is Added",
                isRead = false,
                timestamp = System.currentTimeMillis(),
            )
        )
        serviceRepository.createService(service)
    }

    suspend fun updateService(service: Service):Boolean{
        notificationRepository.create(
            Notification(
                id = 0,
                receiverId = service.serviceProviderId,
                body = "${service.title} is Updated",
                isRead = false,
                timestamp = System.currentTimeMillis(),
            )
        )
        return serviceRepository.updateService(service)
    }
    suspend fun deleteServiceById(serviceId:Long):Boolean{
        val service = serviceRepository.getService(serviceId)
        if(service != null) {
            notificationRepository.create(
                Notification(
                    id = 0,
                    receiverId = service.serviceProviderId,
                    body = "${service.title} is Deleted",
                    isRead = false,
                    timestamp = System.currentTimeMillis(),
                )
            )
        }
        return serviceRepository.deleteServiceById(serviceId)
    }
    suspend fun getServices(): List<Service>{
        return serviceRepository.getServices()
    }

    suspend fun getServicesByEventId(eventId:Long):List<Service>{
        return serviceRepository.getServiceByEventId(eventId)
    }

    suspend fun rateService(serviceRatingInfo: ServiceRatingInfo){
        val service = serviceRepository.getService(serviceRatingInfo.serviceId)
        if(service != null) {
            notificationRepository.create(
                Notification(
                    id = 0,
                    receiverId = service.serviceProviderId,
                    body = "${service.title} is Rated by some one",
                    isRead = false,
                    timestamp = System.currentTimeMillis(),
                )
            )

            notificationRepository.create(
                Notification(
                    id = 0,
                    receiverId = serviceRatingInfo.userId,
                    body = "You rate A services ${serviceRatingInfo.rating}* named ${service.title}",
                    isRead = false,
                    timestamp = System.currentTimeMillis(),
                )
            )
        }
        serviceRepository.rateService(serviceRatingInfo)
    }

    suspend fun getServiceRatingsById(serviceId:Long):List<ServiceRatingInfo>{
        return serviceRepository.getServiceRatingById(serviceId)
    }
    suspend fun getServiceByProviderId(serviceProviderId:Long):List<Service>{
        return serviceRepository.getServiceById(serviceProviderId)
    }
    suspend fun bookService(serviceBookingInfo: ServiceBookingInfo){
        val service = serviceRepository.getService(serviceBookingInfo.serviceId)
        if(service != null) {
            notificationRepository.create(
                Notification(
                    id = 0,
                    receiverId = service.serviceProviderId,
                    body = "A new booking Request",
                    isRead = false,
                    timestamp = System.currentTimeMillis(),
                )
            )

            notificationRepository.create(
                Notification(
                    id = 0,
                    receiverId = serviceBookingInfo.eventOrganizerId,
                    body = "Your booking request is sent to the service provider. Service name ${service.title}",
                    isRead = false,
                    timestamp = System.currentTimeMillis(),
                )
            )
        }
        serviceRepository.bookService(eventOrganizerId = serviceBookingInfo.eventOrganizerId, serviceId = serviceBookingInfo.serviceId)
    }
    suspend fun updateBookingStatus(bookingUpdateInfo: BookingUpdateInfo):Boolean{
        notificationRepository.create(
            Notification(
                id = 0,
                receiverId = bookingUpdateInfo.organizerId,
                body = "Your booking request is ${bookingUpdateInfo.status} by service provider.",
                isRead = false,
                timestamp = System.currentTimeMillis(),
            )
        )
        return serviceRepository.updateBookingStatus(
            id = bookingUpdateInfo.id,
            status = bookingUpdateInfo.status
        )
    }
    suspend fun getBookingByEventId(eventId:Long):List<BookingWithServiceDetails>{
        return serviceRepository.getBookingByEventId(eventId)
    }
    suspend fun  getBookingByServiceProviderId(serviceProviderId:Long):List<BookingWithServiceDetails>{
        return serviceRepository.getBookingByServiceProviderId(serviceProviderId)
    }
}