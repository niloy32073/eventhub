package com.dbytes.models.common

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id:Long,
    val name: String,
    val description: String,
    val organizerId:Long,
    val date:Long,
    val budget:Float
)
