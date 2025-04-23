package com.dbytes.models.common

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id:Long =0,
    val name: String,
    val description: String,
    val organizerId:Long,
    val imageLink: String?,
    val date:Long,
    val budget:Float
)
