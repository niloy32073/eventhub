package com.dbytes.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EventServicesTable: Table("EventServices") {
    val id = long("id").autoIncrement()
    val eventId = reference("eventId", EventTable.id, ReferenceOption.CASCADE)
    val servicesId = reference("serviceId",ServicesTable.id, ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(eventId, servicesId)
}