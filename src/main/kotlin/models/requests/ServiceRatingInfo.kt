package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ServiceRatingInfo(
    val serviceId:Long,
    val userId:Long,
    val rating:Int,
    val feedback:String
)
