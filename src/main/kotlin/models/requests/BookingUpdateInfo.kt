package com.dbytes.models.requests

import com.dbytes.enums.BookingStatus

data class BookingUpdateInfo(
    val id:Long,
    val status: BookingStatus
)
