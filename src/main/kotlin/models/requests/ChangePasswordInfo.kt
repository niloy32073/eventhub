package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordInfo(
    val userId: Long,
    val newPassword: String,
    val oldPassword: String,
)
