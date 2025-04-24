package com.dbytes.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object NotificationsTable: Table("notifications"){
    val id = long("id").autoIncrement()
    val receiverId = reference("receiverId",UserTable.id, ReferenceOption.CASCADE)
    val body = varchar("body", 255)
    val isRead = bool("isRead")
    val timestamp = long("timestamp")
}