package com.dbytes.repositories

import com.dbytes.db.*
import com.dbytes.db.ServiceRatingTable.userId
import com.dbytes.enums.BookingStatus
import com.dbytes.interfaces.ServiceRepositoryInterface
import com.dbytes.models.common.Service
import com.dbytes.models.requests.ServiceRatingInfo
import com.dbytes.models.responses.BookingWithServiceDetails
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ServiceRepository:ServiceRepositoryInterface {
    override suspend fun createService(service: Service) {
        transaction {
            ServicesTable.insert {
                it[title] = service.title
                it[description] = service.description
                it[serviceProviderId] = service.serviceProviderId
                it[fee] = service.fee
                it[serviceType] = service.serviceType
                it[imageLink] = service.imageLink
            }
        }
    }

    override suspend fun updateService(service: Service): Boolean = transaction {
        ServicesTable.update({ServicesTable.id eq service.id}) {
            it[title] = service.title
            it[description] = service.description
            it[serviceProviderId] = service.serviceProviderId
            it[fee] = service.fee
            it[serviceType] = service.serviceType
            it[imageLink] = service.imageLink
        } > 0
    }

    override suspend fun deleteServiceById(serviceId: Long): Boolean = transaction {
        ServicesTable.deleteWhere { id eq serviceId } > 0
    }

    override suspend fun rateService(serviceRatingInfo: ServiceRatingInfo){
        transaction {
            ServiceRatingTable.insert {
                it[servicesId] = serviceRatingInfo.serviceId
                it[ServiceRatingTable.userId] = serviceRatingInfo.userId
                it[ServiceRatingTable.rating] = serviceRatingInfo.rating
                it[ServiceRatingTable.feedback] = serviceRatingInfo.feedback
            }
        }
    }

    override suspend fun getServiceRatingById(serviceId: Long): List<ServiceRatingInfo> = transaction {
        ServiceRatingTable.selectAll().where { ServiceRatingTable.servicesId eq serviceId }.map {
            ServiceRatingInfo(
                userId = it[ServiceRatingTable.userId],
                rating = it[ServiceRatingTable.rating],
                serviceId = it[ServiceRatingTable.servicesId],
                feedback = it[ServiceRatingTable.feedback] ?:"No feedback"
            )
        }
    }

    override suspend fun getServices(): List<Service> = transaction {
        // First fetch all services
        val services = ServicesTable.selectAll().map { row ->
            val serviceId = row[ServicesTable.id]

            // Compute average rating for the current service
            val avgRating = ServiceRatingTable
                .selectAll()
                .where{ ServiceRatingTable.servicesId eq serviceId }
                .map { it[ServiceRatingTable.rating].toDouble() }
                .takeIf { it.isNotEmpty() }
                ?.average()
                ?.toFloat() ?: 0f

            Service(
                id = serviceId,
                title = row[ServicesTable.title],
                description = row[ServicesTable.description],
                rating = avgRating,
                serviceProviderId = row[ServicesTable.serviceProviderId],
                fee = row[ServicesTable.fee],
                imageLink = row[ServicesTable.imageLink],
                serviceType = row[ServicesTable.serviceType]
            )
        }

        services
    }

    override suspend fun getService(serviceId: Long): Service? = transaction {
        val avgRating = ServiceRatingTable
            .selectAll()
            .where { ServiceRatingTable.servicesId eq serviceId }
            .map { it[ServiceRatingTable.rating].toDouble() }
            .takeIf { it.isNotEmpty() }
            ?.average()
            ?.toFloat() ?: 0f

        ServicesTable.selectAll().where { ServicesTable.id eq serviceId }.map {
            Service(
                id = serviceId,
                title = it[ServicesTable.title],
                description = it[ServicesTable.description],
                rating = avgRating,
                serviceProviderId = it[ServicesTable.serviceProviderId],
                fee = it[ServicesTable.fee],
                imageLink = it[ServicesTable.imageLink],
                serviceType = it[ServicesTable.serviceType]
            )
        }.firstOrNull()
    }

    override suspend fun getServiceById(serviceProviderId: Long): List<Service> = transaction {
        // First fetch all services
        val services = ServicesTable.selectAll().where{ServicesTable.serviceProviderId eq serviceProviderId}.map { row ->
            val serviceId = row[ServicesTable.id]

            // Compute average rating for the current service
            val avgRating = ServiceRatingTable
                .selectAll()
                .where{ ServiceRatingTable.servicesId eq serviceId }
                .map { it[ServiceRatingTable.rating].toDouble() }
                .takeIf { it.isNotEmpty() }
                ?.average()
                ?.toFloat() ?: 0f

            Service(
                id = serviceId,
                title = row[ServicesTable.title],
                description = row[ServicesTable.description],
                rating = avgRating,
                serviceProviderId = row[ServicesTable.serviceProviderId],
                fee = row[ServicesTable.fee],
                imageLink = row[ServicesTable.imageLink],
                serviceType = row[ServicesTable.serviceType]
            )
        }

        services
    }

    override suspend fun getServiceByEventId(eventId: Long): List<Service>  = transaction{
        val serviceIds = EventServicesTable.selectAll().where { EventServicesTable.eventId eq eventId }.map { it[EventServicesTable.servicesId]
        }
        ServicesTable.selectAll().where { ServicesTable.id inList serviceIds }.map {
            val serviceId = it[ServicesTable.id]
            val avgRating = ServiceRatingTable
                .selectAll()
                .where{ ServiceRatingTable.servicesId eq serviceId }
                .map { it[ServiceRatingTable.rating].toDouble() }
                .takeIf { it.isNotEmpty() }
                ?.average()
                ?.toFloat() ?: 0f

            Service(
                id = serviceId,
                title = it[ServicesTable.title],
                description = it[ServicesTable.description],
                rating = avgRating,
                serviceProviderId = it[ServicesTable.serviceProviderId],
                fee = it[ServicesTable.fee],
                imageLink = it[ServicesTable.imageLink],
                serviceType = it[ServicesTable.serviceType]
            )
        }
    }

    override suspend fun bookService(eventOrganizerId: Long, serviceId: Long){
        transaction {
            ServiceBookingTable.insert {
                it[ServiceBookingTable.eventOrganizerId] = eventOrganizerId
                it[ServiceBookingTable.serviceId] = serviceId
                it[ServiceBookingTable.status] = BookingStatus.PENDING
            }
        }
    }

    override suspend fun updateBookingStatus(id: Long, status: BookingStatus): Boolean = transaction{
        ServiceBookingTable.update({ServiceBookingTable.id eq id}) {
            it[ServiceBookingTable.status] = status
        } > 0
    }

    override suspend fun getBookingByEventId(eventId:Long):List<BookingWithServiceDetails> = transaction {
        val providerAlias = UserTable.alias("provider")
        val organizerAlias = UserTable.alias("organizer")

        val result = (EventServicesTable
            .innerJoin(ServicesTable, { EventServicesTable.servicesId }, { ServicesTable.id })
            .innerJoin(ServiceBookingTable, { ServicesTable.id }, { ServiceBookingTable.serviceId })
            .innerJoin(providerAlias, { ServicesTable.serviceProviderId }, { providerAlias[UserTable.id] })
            .innerJoin(EventTable, { EventServicesTable.eventId }, { EventTable.id })
            .innerJoin(organizerAlias, { EventTable.organizerId }, { organizerAlias[UserTable.id] })
                )
            .selectAll().where { EventTable.id eq eventId }
            .map {
                BookingWithServiceDetails(
                    id = it[ServicesTable.id],
                    title = it[ServicesTable.title],
                    description = it[ServicesTable.description],
                    serviceProviderName = it[providerAlias[UserTable.name]],
                    fee = it[ServicesTable.fee],
                    imageLink = it[ServicesTable.imageLink],
                    serviceType = it[ServicesTable.serviceType],
                    eventOrganizerName = it[organizerAlias[UserTable.name]],
                    eventName = it[EventTable.name],
                    eventDate = it[EventTable.date],
                    bookingId = it[ServiceBookingTable.id],
                    bookingStatus = it[ServiceBookingTable.status]
                )
            }
        result
    }


    override suspend fun getBookingByServiceProviderId(serviceProviderId: Long): List<BookingWithServiceDetails> = transaction {
        val providerAlias = UserTable.alias("provider")
        val organizerAlias = UserTable.alias("organizer")
        val eventServicesAlias = EventServicesTable.alias("eventServices")

        ServiceBookingTable
            .join(ServicesTable, JoinType.INNER, ServiceBookingTable.serviceId, ServicesTable.id)
            .join(providerAlias, JoinType.INNER, ServicesTable.serviceProviderId, providerAlias[UserTable.id])
            .join(organizerAlias, JoinType.INNER, ServiceBookingTable.eventOrganizerId, organizerAlias[UserTable.id])
            .join(eventServicesAlias, JoinType.INNER, ServiceBookingTable.serviceId, eventServicesAlias[EventServicesTable.servicesId])
            .join(EventTable, JoinType.INNER, eventServicesAlias[EventServicesTable.eventId], EventTable.id)
            .selectAll()
            .where { ServicesTable.serviceProviderId eq serviceProviderId }
            .map { row ->
                BookingWithServiceDetails(
                    id = row[ServiceBookingTable.id], // Use booking ID, not service ID
                    title = row[ServicesTable.title],
                    description = row[ServicesTable.description],
                    serviceProviderName = row[providerAlias[UserTable.name]],
                    fee = row[ServicesTable.fee],
                    imageLink = row[ServicesTable.imageLink],
                    serviceType = row[ServicesTable.serviceType],
                    eventOrganizerName = row[organizerAlias[UserTable.name]],
                    eventName = row[EventTable.name],
                    eventDate = row[EventTable.date],
                    bookingId = row[ServiceBookingTable.id],
                    bookingStatus = row[ServiceBookingTable.status]
                )
            }
            .distinct() // Ensure no duplicate bookings
    }
}