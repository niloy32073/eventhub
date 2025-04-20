package com.dbytes.interfaces

import com.dbytes.enums.BookingStatus
import com.dbytes.models.common.Service

interface ServiceRepositoryInterface {
    suspend fun createService(service: Service):Boolean
    suspend fun updateService(service: Service): Boolean
    suspend fun deleteServiceById(serviceId: Long): Boolean
    suspend fun rateService(serviceId:Long,userId:Long,rating:Int): Boolean
    suspend fun getServices(): List<Service>
    suspend fun getServiceBookingById(serviceProviderId:Long):List<Service>
    suspend fun bookService(eventOrganizerId:Long,serviceId:Long):Boolean
    suspend fun updateBookingStatus(serviceId:Long,status: BookingStatus):Boolean
}