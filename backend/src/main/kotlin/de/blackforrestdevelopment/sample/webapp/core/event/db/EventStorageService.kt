package de.blackforrestdevelopment.sample.webapp.core.event.db


import de.blackforrestdevelopment.sample.webapp.common.BaseStorageService
import de.blackforrestdevelopment.sample.webapp.common.PatchRequest
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account
import de.blackforrestdevelopment.sample.webapp.core.event.api.Event
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventChangeRequest
import de.blackforrestdevelopment.sample.webapp.error.InvalidRequestException
import de.blackforrestdevelopment.sample.webapp.infrastructure.time.TimeProvider
import de.sambalmueslie.openevent.infrastructure.cache.CacheService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class EventStorageService(
    private val repository: EventRepository,
    private val converter: EventConverter,

    cacheService: CacheService,
    private val timeProvider: TimeProvider,
) : BaseStorageService<Long, Event, EventChangeRequest, EventData>(
    repository,
    converter,
    cacheService,
    Event::class,
    logger
), EventStorage {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventStorageService::class.java)
        private const val OWNER_REFERENCE = "owner"
    }

    override fun create(request: EventChangeRequest, owner: Account): Event {
        return create(request, mapOf(Pair(OWNER_REFERENCE, owner)))
    }

    override fun createData(request: EventChangeRequest, properties: Map<String, Any>): EventData {
        val owner = properties[OWNER_REFERENCE] as? Account ?: throw InvalidRequestException("Cannot find account")
        return EventData.create(owner, request, timeProvider.now())
    }

    override fun updateData(data: EventData, request: EventChangeRequest): EventData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: EventChangeRequest) {
        if (request.title.isBlank()) throw InvalidRequestException("Title cannot be blank")
    }

    override fun setPublished(id: Long, value: PatchRequest<Boolean>): Event? {
        return patchData(id) { it.setPublished(value.value, timeProvider.now()) }
    }

    override fun getAllForAccount(account: Account, pageable: Pageable): Page<Event> {
        return repository.findForUser(account.id, pageable).let { converter.convert(it) }
    }

    override fun getOwned(owner: Account, pageable: Pageable): Page<Event> {
        return repository.findByOwnerId(owner.id, pageable).let { converter.convert(it) }
    }

    override fun getAll(pageable: Pageable): Page<Event> {
        return repository.findAllOrderByStart(pageable).let { converter.convert(it) }
    }


}
