package com.dbytes.interfaces

import com.dbytes.models.common.Event
import com.dbytes.models.common.Service

interface EventRepositoryInterface {
    suspend fun createEvent(event: Event)
    suspend fun updateEvent(event: Event):Boolean
    suspend fun deleteEvent(eventId: Long):Boolean
    suspend fun addServicesToEvent(eventId:Long,serviceId:Long)
    suspend fun removeServicesFromEvent(eventId:Long,serviceId:Long):Boolean
    suspend fun getEventsByUserId(id:Long):List<Event>
    suspend fun getEventCost(eventId:Long):Float
}