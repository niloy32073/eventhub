package com.dbytes.db

import com.dbytes.db.UserTable.autoIncrement
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EventTable: Table("Events") {
    val id = long("id").autoIncrement()
    val name = varchar("event_name", 255)
    val description = varchar("event_description", 255)
    val date = long("date")
    val organizerId = reference("organizerId",UserTable.id, ReferenceOption.CASCADE)
    val budget = float("budget")
}