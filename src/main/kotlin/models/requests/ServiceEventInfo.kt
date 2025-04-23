package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ServiceEventInfo(
    val eventId:Long,
    val serviceId:Long
)
