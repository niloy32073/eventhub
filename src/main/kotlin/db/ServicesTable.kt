package com.dbytes.db

import com.dbytes.enums.ServiceType
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ServicesTable: Table("Services") {
    val id = long("id").autoIncrement()
    val serviceProviderId = reference("serviceProviderId",UserTable.id, ReferenceOption.CASCADE)
    val serviceType = enumerationByName("serviceType", 20, ServiceType::class)
    val title = text("title")
    val description = text("description")
    val imageLink = varchar("imageLink", 255).nullable()
    val fee = float("fee").default(0f)

    override val primaryKey = PrimaryKey(id)
}