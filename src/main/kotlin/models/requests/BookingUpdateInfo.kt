package com.dbytes.models.requests

import com.dbytes.enums.BookingStatus
import kotlinx.serialization.Serializable

@Serializable
data class BookingUpdateInfo(
    val id:Long,
    val status: BookingStatus
)
