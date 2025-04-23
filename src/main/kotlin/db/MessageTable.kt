package com.dbytes.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object MessageTable:Table("Messages") {
    val id = long("id").autoIncrement()
    val senderId = reference("senderId",UserTable.id, ReferenceOption.CASCADE)
    val isAi = bool("isAi")
    val receiverId = reference("receiverId",UserTable.id, ReferenceOption.CASCADE).nullable()
    val text = varchar("text", 255)
    val ImageLink = varchar("imageLink", 255).nullable()
    val sentAt = long("sentAt")
    override val primaryKey = PrimaryKey(id)
}
