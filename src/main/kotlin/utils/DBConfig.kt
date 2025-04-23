package com.dbytes.utils

import com.dbytes.db.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DBConfig {
    fun connect() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/postgres",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "32o73"
        )
        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(ServicesTable)
            SchemaUtils.create(ServiceBookingTable)
            SchemaUtils.create(ServiceRatingTable)
            SchemaUtils.create(EventTable)
            SchemaUtils.create(EventServicesTable)
            SchemaUtils.create(MessageTable)
        }
    }
}