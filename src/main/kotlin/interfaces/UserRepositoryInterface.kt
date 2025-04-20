package com.dbytes.interfaces

import com.dbytes.models.common.User

interface UserRepositoryInterface {
    suspend fun getUserById(id: Long): User?
    suspend fun deleteUserById(id:Long)
    suspend fun checkUserPasswordById(id:Long,oldPassword:String):Boolean
    suspend fun changeUserPasswordById(id:Long, newPassword: String)
    suspend fun updateUserNameById(id:Long, name:String)
    suspend fun updateUserPhoneById(id:Long, phone:String)
}