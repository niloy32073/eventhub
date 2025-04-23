package com.dbytes.db

import com.dbytes.enums.Role
import org.jetbrains.exposed.sql.Table

object UserTable: Table("users") {
    val id = long("id").autoIncrement()
    val name = varchar("username", 255)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val phone = varchar("phone", 15)
    val role = enumerationByName("role",25, Role::class)
    override val primaryKey = PrimaryKey(id)
}