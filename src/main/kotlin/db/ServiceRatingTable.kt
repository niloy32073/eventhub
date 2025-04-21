package com.dbytes.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ServiceRatingTable: Table("ServiceRatings") {
    val servicesId = reference("serviceId",ServicesTable.id, ReferenceOption.CASCADE)
    val userId = reference("userId",UserTable.id, ReferenceOption.CASCADE)
    val rating = integer("rating")
    val feedback = varchar("feedback",256).nullable()
}