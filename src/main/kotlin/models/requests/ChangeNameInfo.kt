package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeNameInfo(
    val id: Long,
    val name: String
)
