package com.dbytes.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserBasicInfo(
    val userId : Long,
    val name : String
)
