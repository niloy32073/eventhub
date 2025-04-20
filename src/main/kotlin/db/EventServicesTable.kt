package com.dbytes.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EventServicesTable: Table("EventServices") {
    val eventId = reference("eventId", EventTable.id, ReferenceOption.CASCADE)
    val servicesId = reference("serviceId",ServicesTable.id, ReferenceOption.CASCADE)
}