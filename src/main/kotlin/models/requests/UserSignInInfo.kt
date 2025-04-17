package com.dbytes.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserSignInInfo(val email: String,val password:String)