package com.dbytes.services

import com.dbytes.interfaces.UserRepositoryInterface
import com.dbytes.models.common.User

class UserServices(private val userRepository: UserRepositoryInterface) {
    suspend fun getUserById(id: Long): User? {
        return userRepository.getUserById(id)
    }
    suspend fun checkUserPassword(id: Long, oldPassword: String): Boolean {
        return userRepository.checkUserPasswordById(id, oldPassword)
    }
    suspend fun updateUserPassword(id: Long, newPassword: String) {
        userRepository.changeUserPasswordById(id, newPassword)
    }
    suspend fun deleteUserById(id: Long) {
        userRepository.deleteUserById(id)
    }

    suspend fun updateUserNameById(id: Long, name: String) {
        userRepository.updateUserNameById(id, name)
    }
    suspend fun updateUserPhoneById(id: Long, phone: String) {
        userRepository.updateUserPhoneById(id, phone)
    }
}