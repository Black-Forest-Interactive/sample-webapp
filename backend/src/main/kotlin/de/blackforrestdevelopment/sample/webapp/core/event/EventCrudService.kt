package de.blackforrestdevelopment.sample.webapp.core.event


import de.blackforrestdevelopment.sample.webapp.common.BaseCrudService
import de.blackforrestdevelopment.sample.webapp.common.PatchRequest
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account
import de.blackforrestdevelopment.sample.webapp.core.event.api.Event
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventChangeRequest
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventInfo
import de.blackforrestdevelopment.sample.webapp.core.event.db.EventStorage
import de.blackforrestdevelopment.sample.webapp.logTimeMillisWithValue
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class EventCrudService(
    private val storage: EventStorage,
) : BaseCrudService<Long, Event, EventChangeRequest, EventChangeListener>(storage) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventCrudService::class.java)
    }


    fun setPublished(actor: Account, id: Long, value: PatchRequest<Boolean>): Event? {
        val result = storage.setPublished(id, value) ?: return null
        notify { it.publishedChanged(actor, result) }
        return result
    }

    fun getInfo(id: Long, account: Account?): EventInfo? {
        return logger.logTimeMillisWithValue("[$id] get event info") {
            get(id)?.let { getInfo(it, account) }
        }
    }

    fun getInfo(event: Event, account: Account?): EventInfo {
        val canEdit = event.owner.id == account?.id
        return EventInfo(event, canEdit)
    }

    fun getInfos(pageable: Pageable): Page<EventInfo> {
        return convertInfo(getAll(pageable))
    }

    fun getAllForAccount(account: Account, pageable: Pageable): Page<Event> {
        return storage.getAllForAccount(account, pageable)
    }

    fun getOwned(owner: Account, pageable: Pageable): Page<Event> {
        return storage.getOwned(owner, pageable)
    }

    fun getInfosForAccount(account: Account, pageable: Pageable): Page<EventInfo> {
        return convertInfo(getAllForAccount(account, pageable), account)
    }

    private fun convertInfo(events: Page<Event>, account: Account? = null): Page<EventInfo> {
        val canEdit = events.content.associate { it.id to (it.owner.id == account?.id) }
        return events.map {
            EventInfo(
                it,
                canEdit[it.id] ?: false
            )
        }
    }


}
