package com.dbytes.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserLogInResult(val userId: Long,val roles: String,val token: String)
