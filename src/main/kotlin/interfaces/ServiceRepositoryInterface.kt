package com.dbytes.interfaces

import com.dbytes.enums.BookingStatus
import com.dbytes.models.common.Service
import com.dbytes.models.requests.ServiceRatingInfo
import com.dbytes.models.responses.BookingWithServiceDetails
import com.dbytes.models.responses.ServiceEvent

interface ServiceRepositoryInterface {
    suspend fun createService(service: Service)
    suspend fun updateService(service: Service): Boolean
    suspend fun deleteServiceById(serviceId: Long): Boolean
    suspend fun rateService(serviceRatingInfo: ServiceRatingInfo)
    suspend fun getServiceRatingById(serviceId: Long): List<ServiceRatingInfo>
    suspend fun getServices(): List<Service>
    suspend fun getService(serviceId: Long): Service?
    suspend fun getServiceById(serviceProviderId:Long):List<Service>
    suspend fun getServiceByEventId(eventId: Long): List<ServiceEvent>
    suspend fun bookService(eventOrganizerId:Long,serviceId:Long)
    suspend fun updateBookingStatus(id:Long,status: BookingStatus):Boolean
    suspend fun getBookingByEventId(eventId:Long):List<BookingWithServiceDetails>
    suspend fun getBookingByServiceProviderId(serviceProviderId:Long):List<BookingWithServiceDetails>
}