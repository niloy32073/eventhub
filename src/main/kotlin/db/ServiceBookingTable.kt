package com.dbytes.db

import com.dbytes.enums.BookingStatus
import com.dbytes.enums.ServiceType
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ServiceBookingTable: Table("ServiceBookings") {
    val id = long("id").autoIncrement()
    val serviceId = reference("serviceId",ServicesTable.id, ReferenceOption.CASCADE)
    val eventOrganizerId = reference("eventOrganizerId",UserTable.id, ReferenceOption.CASCADE)
    val status = enumerationByName("status", 20, BookingStatus::class)
}