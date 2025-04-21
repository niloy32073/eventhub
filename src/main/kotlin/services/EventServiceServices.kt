package com.dbytes.services

import com.dbytes.enums.BookingStatus
import com.dbytes.interfaces.EventRepositoryInterface
import com.dbytes.interfaces.ServiceRepositoryInterface
import com.dbytes.models.common.Event
import com.dbytes.models.common.Service
import com.dbytes.models.requests.BookingUpdateInfo
import com.dbytes.models.requests.ServiceBookingInfo
import com.dbytes.models.requests.ServiceEventInfo
import com.dbytes.models.requests.ServiceRatingInfo
import com.dbytes.models.responses.BookingWithServiceDetails

class EventServiceServices (private val eventRepository: EventRepositoryInterface, private val serviceRepository: ServiceRepositoryInterface) {
    suspend fun createEvent(event: Event){
        eventRepository.createEvent(event)
    }

    suspend fun updateEvent(event: Event):Boolean{
        return eventRepository.updateEvent(event)
    }

    suspend fun deleteEvent(id:Long):Boolean{
        return eventRepository.deleteEvent(id)
    }

    suspend fun getEventsByUserId(id:Long):List<Event>{
        return eventRepository.getEventsByUserId(id)
    }

    suspend fun getEventCost(id:Long):Float{
        return eventRepository.getEventCost(id)
    }

    suspend fun addServiceToEvent(serviceEventInfo: ServiceEventInfo){
        eventRepository.addServicesToEvent(eventId = serviceEventInfo.eventId,serviceId = serviceEventInfo.serviceId)
    }
    suspend fun removeServiceFromEvent(serviceEventInfo: ServiceEventInfo){
        eventRepository.removeServicesFromEvent(eventId = serviceEventInfo.eventId,serviceId = serviceEventInfo.serviceId)
    }

    suspend fun createService(service: Service){
        serviceRepository.createService(service)
    }

    suspend fun updateService(service: Service):Boolean{
        return serviceRepository.updateService(service)
    }
    suspend fun deleteServiceById(serviceId:Long):Boolean{
        return serviceRepository.deleteServiceById(serviceId)
    }
    suspend fun getServices(): List<Service>{
        return serviceRepository.getServices()
    }

    suspend fun rateService(serviceRatingInfo: ServiceRatingInfo){
        serviceRepository.rateService(serviceRatingInfo)
    }

    suspend fun getServiceRatingsById(serviceId:Long):List<ServiceRatingInfo>{
        return serviceRepository.getServiceRatingById(serviceId)
    }
    suspend fun getServiceByProviderId(serviceProviderId:Long):List<Service>{
        return serviceRepository.getServiceById(serviceProviderId)
    }
    suspend fun bookService(serviceBookingInfo: ServiceBookingInfo){
        serviceRepository.bookService(eventOrganizerId = serviceBookingInfo.eventOrganizerId, serviceId = serviceBookingInfo.serviceId)
    }
    suspend fun updateBookingStatus(bookingUpdateInfo: BookingUpdateInfo):Boolean{
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