package com.dbytes.models.requests

data class ServiceRatingInfo(
    val serviceId:Long,
    val userId:Long,
    val rating:Int,
    val feedback:String
)
