package de.blackforrestdevelopment.sample.webapp.core.event.api

import de.blackforrestdevelopment.sample.webapp.common.CrudAPI
import de.blackforrestdevelopment.sample.webapp.common.PatchRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication

interface EventAPI : CrudAPI<Long, Event, EventChangeRequest> {
    companion object {
        const val PERMISSION_READ = "event.read"
        const val PERMISSION_WRITE = "event.write"
        const val PERMISSION_ADMIN = "event.admin"
        const val PERMISSION_MODERATOR = "event.mod"
    }

    fun setPublished(auth: Authentication, id: Long, value: PatchRequest<Boolean>): Event?
    fun getInfo(auth: Authentication, id: Long): EventInfo?
    fun getInfos(auth: Authentication, pageable: Pageable): Page<EventInfo>

    fun create(auth: Authentication, ownerId: Long, request: EventChangeRequest): Event
}
