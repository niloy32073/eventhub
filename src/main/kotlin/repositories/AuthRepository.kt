package com.dbytes.repositories

import com.dbytes.db.UserTable
import com.dbytes.interfaces.AuthRepositoryInterface
import com.dbytes.models.common.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AuthRepository:AuthRepositoryInterface {
    override suspend fun createUser(user: User): User = transaction {
        val userId = UserTable.insert {
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
            it[phone] = user.phone
            it[role] = user.role
        } get UserTable.id
        user.copy(id = userId)
    }

    override suspend fun findUserByEmail(email: String): User? = transaction {
        UserTable.selectAll().where { UserTable.email eq email }.map{
            User(id = it[UserTable.id],name = it[UserTable.name], email = it[UserTable.email], phone = it[UserTable.phone],password = it[UserTable.password], role = it[UserTable.role])
        }.singleOrNull()
    }
}