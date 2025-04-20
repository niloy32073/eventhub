package com.dbytes.models.common

import com.dbytes.enums.ServiceType

data class Service(
    val id: Long,
    val title: String,
    val description: String,
    val rating: Float,
    val serviceProviderId: Long,
    val fee: Float,
    val imageLink: String?,
    val serviceType: ServiceType
)
