package com.dbytes.models.requests

import com.dbytes.enums.BookingStatus
import kotlinx.serialization.Serializable

@Serializable
data class ServiceBookingInfo(
    val serviceId:Long,
    val eventOrganizerId:Long,
    val status: BookingStatus
)
