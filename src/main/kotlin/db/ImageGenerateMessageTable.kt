package com.dbytes.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ImageGenerateMessageTable: Table("ImageGenerateMessages") {
    val id = long("id").autoIncrement()
    val senderId = reference("senderId",UserTable.id, ReferenceOption.CASCADE)
    val text = varchar("text", 1024)
    val imageLink = varchar("imageLink", 255).nullable()
}