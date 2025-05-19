package com.dbytes.models.responses

import com.dbytes.enums.ServiceType
import kotlinx.serialization.Serializable

@Serializable
data class ServiceEvent(
    val id: Long = 0,
    val serviceEventId:Long,
    val title: String,
    val description: String,
    val rating: Float,
    val serviceProviderId: Long,
    val fee: Float,
    val imageLink: String?,
    val serviceType: ServiceType
)
