package com.dbytes.interfaces

import com.dbytes.models.common.Event
import com.dbytes.models.common.Service

interface EventRepositoryInterface {
    suspend fun createEvent(event: Event):Boolean
    suspend fun updateEvent(event: Event):Boolean
    suspend fun deleteEvent(event: Event):Boolean
    suspend fun addServicesToEvent(eventId:Long,serviceId:Long):Boolean
    suspend fun removeServicesFromEvent(eventId:Long,serviceId:Long):Boolean
    suspend fun getServicesByEventId(id:Long):List<Service>
    suspend fun getEventsByUserId(id:Long):List<Event>
}