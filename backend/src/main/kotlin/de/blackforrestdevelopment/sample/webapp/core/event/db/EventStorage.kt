package de.blackforrestdevelopment.sample.webapp.core.event.db

import de.blackforrestdevelopment.sample.webapp.common.PatchRequest
import de.blackforrestdevelopment.sample.webapp.common.Storage
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account
import de.blackforrestdevelopment.sample.webapp.core.event.api.Event
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface EventStorage : Storage<Long, Event, EventChangeRequest> {
    fun create(request: EventChangeRequest, owner: Account): Event

    fun setPublished(id: Long, value: PatchRequest<Boolean>): Event?

    fun getAllForAccount(account: Account, pageable: Pageable): Page<Event>
    fun getOwned(owner: Account, pageable: Pageable): Page<Event>


}
