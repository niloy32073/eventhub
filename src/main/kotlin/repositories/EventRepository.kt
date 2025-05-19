package com.dbytes.repositories

import com.dbytes.db.EventServicesTable
import com.dbytes.db.EventTable
import com.dbytes.db.ServicesTable
import com.dbytes.interfaces.EventRepositoryInterface
import com.dbytes.models.common.Event
import com.dbytes.models.common.Service
import io.ktor.events.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.transaction

class EventRepository: EventRepositoryInterface {
    override suspend fun createEvent(event: Event){
        transaction {
            EventTable.insert {
                it[EventTable.name] = event.name
                it[EventTable.date] = event.date
                it[EventTable.budget] = event.budget
                it[EventTable.description] = event.description
                it[EventTable.organizerId] = event.organizerId
                it[EventTable.imageLink] = event.imageLink
            }
        }
    }

    override suspend fun updateEvent(event: Event): Boolean = transaction {
        EventTable.update({EventTable.id eq event.id}) {
            it[EventTable.name] = event.name
            it[EventTable.date] = event.date
            it[EventTable.budget] = event.budget
            it[EventTable.description] = event.description
            it[EventTable.organizerId] = event.organizerId
            it[EventTable.imageLink] = event.imageLink
        } > 0
    }

    override suspend fun deleteEvent(eventId: Long): Boolean = transaction {
        EventTable.deleteWhere{id eq eventId} > 0
    }

    override suspend fun addServicesToEvent(eventId: Long, serviceId: Long) {
        transaction {
            EventServicesTable.insert {
                it[EventServicesTable.servicesId] = serviceId
                it[EventServicesTable.eventId] = eventId
            }
        }
    }

    override suspend fun removeServicesFromEvent(eventServiceId: Long): Boolean  = transaction{
        EventServicesTable.deleteWhere { EventServicesTable.id eq eventServiceId } > 0
    }

    override suspend fun getEventsByUserId(id: Long): List<Event> = transaction {
        EventTable.selectAll().where{EventTable.organizerId eq id}.map {
            Event(
                id = it[EventTable.id],
                name = it[EventTable.name],
                date = it[EventTable.date],
                description = it[EventTable.description],
                organizerId = it[EventTable.organizerId],
                budget = it[EventTable.budget],
                imageLink = it[EventTable.imageLink],
            )
        }
    }

    override suspend fun getEventCost(eventId: Long): Float = transaction{
        val serviceIds = EventServicesTable.select(EventServicesTable.servicesId).where(EventServicesTable.eventId eq eventId).map { it[EventServicesTable.servicesId] }
        val serviceFees = ServicesTable.select(ServicesTable.fee).where(ServicesTable.id inList serviceIds).map { it[ServicesTable.fee ]}
        serviceFees.sum()
    }

    override suspend fun getEvent(id: Long): Event? = transaction {
        EventTable.selectAll().where{EventTable.id eq id}.map {
            Event(
                id = it[EventTable.id],
                name = it[EventTable.name],
                date = it[EventTable.date],
                description = it[EventTable.description],
                organizerId = it[EventTable.organizerId],
                budget = it[EventTable.budget],
                imageLink = it[EventTable.imageLink],
            )
        }.firstOrNull()
    }
}