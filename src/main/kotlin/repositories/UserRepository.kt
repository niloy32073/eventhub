package com.dbytes.repositories

import com.dbytes.db.UserTable
import com.dbytes.interfaces.UserRepositoryInterface
import com.dbytes.models.common.User
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class UserRepository :UserRepositoryInterface {
    override suspend fun getUserById(id: Long): User? = transaction {
        UserTable.selectAll().where { UserTable.id eq id }.map {
            User(
                id = it[UserTable.id],
                name = it[UserTable.name],
                email = it[UserTable.email],
                phone = it[UserTable.phone],
                role =  it[UserTable.role],
                password =  it[UserTable.password],
            )
        }.singleOrNull()
    }

    override suspend fun deleteUserById(id: Long) {
        transaction {
            UserTable.deleteWhere { UserTable.id eq id }
        }
    }

    override suspend fun checkUserPasswordById(id: Long, oldPassword: String): Boolean {
        val idExist = transaction {UserTable.select(UserTable.id).where { (UserTable.id eq id)  and (UserTable.password eq oldPassword)}.map {
            it[UserTable.id]
        }.singleOrNull()}
        return idExist != null
    }

    override suspend fun changeUserPasswordById(id: Long, newPassword: String) {
        transaction {
            UserTable.update({ UserTable.id eq id }) {
                it[UserTable.password] = newPassword
            }
        }
    }

    override suspend fun updateUserNameById(id: Long, name: String) {
        transaction {
            UserTable.update({ UserTable.id eq id }) {
                it[UserTable.name] = name
            }
        }
    }

    override suspend fun updateUserPhoneById(id: Long, phone: String) {
        transaction {
            UserTable.update({ UserTable.id eq id }) {
                it[UserTable.phone] = phone
            }
        }
    }
}