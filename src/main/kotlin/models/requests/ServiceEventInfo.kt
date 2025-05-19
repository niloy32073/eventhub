package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ServiceEventInfo(
    val id:Long,
    val eventId:Long,
    val serviceId:Long
)
