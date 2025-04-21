package com.dbytes.models.responses

import com.dbytes.enums.BookingStatus
import com.dbytes.enums.ServiceType

data class BookingWithServiceDetails(
    val id: Long,
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
