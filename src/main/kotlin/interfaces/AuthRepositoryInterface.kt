package com.dbytes.interfaces

import com.dbytes.models.common.User


interface AuthRepositoryInterface {
    suspend fun createUser(user: User): User
    suspend fun findUserByEmail(email:String): User?
}