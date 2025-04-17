package com.dbytes.services

import com.dbytes.interfaces.AuthRepositoryInterface
import com.dbytes.models.common.User
import com.dbytes.models.requests.UserSignInInfo

class AuthServices(private val authRepository: AuthRepositoryInterface) {
    suspend fun registerUser(user: User): User {
        val existingUser = authRepository.findUserByEmail(user.email)
        if (existingUser != null) {
            throw IllegalArgumentException("User with ${user.email} already exists.")
        }

        val newUser = authRepository.createUser(user)
        return newUser
    }

    suspend fun loginUser(userSignInInfo: UserSignInInfo): User {
        val existingUser = authRepository.findUserByEmail(userSignInInfo.email)
            ?: throw IllegalArgumentException("User doesn't exist with ${userSignInInfo.email}.")
        if (existingUser.password != userSignInInfo.password)
            throw IllegalArgumentException("Incorrect password.")
        else
            return existingUser
    }
}