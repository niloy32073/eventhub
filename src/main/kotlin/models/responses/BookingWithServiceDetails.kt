package com.dbytes.models.responses

import com.dbytes.enums.BookingStatus
import com.dbytes.enums.ServiceType
import kotlinx.serialization.Serializable

@Serializable
data class BookingWithServiceDetails(
    val id: Long,
    val eventServiceId:Long,
    val title: String,
    val description: String,
    val serviceProviderName: String?,
    val fee: Float,
    val imageLink: String?,
    val serviceType: ServiceType,
    val eventOrganizerName:String?,
    val eventName:String,
    val eventDate: Long,
    val bookingId:Long,
    val bookingStatus: BookingStatus,
)
