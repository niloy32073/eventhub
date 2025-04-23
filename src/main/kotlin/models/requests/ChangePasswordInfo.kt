package com.dbytes.models.requests

data class ChangePasswordInfo(
    val userId: Long,
    val newPassword: String,
    val oldPassword: String,
)
