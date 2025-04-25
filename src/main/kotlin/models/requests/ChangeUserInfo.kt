package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeUserInfo(
    val id: Long,
    val name: String,
    val phone: String
)
