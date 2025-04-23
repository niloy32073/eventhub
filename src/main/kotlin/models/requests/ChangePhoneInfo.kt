package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangePhoneInfo(
    val id: Long,
    val phone: String,
)
