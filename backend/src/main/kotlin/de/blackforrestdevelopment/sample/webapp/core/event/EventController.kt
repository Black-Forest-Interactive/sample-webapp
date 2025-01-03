package de.blackforrestdevelopment.sample.webapp.core.event


import de.blackforrestdevelopment.sample.webapp.common.PatchRequest
import de.blackforrestdevelopment.sample.webapp.common.checkPermission
import de.blackforrestdevelopment.sample.webapp.common.getRealmRoles
import de.blackforrestdevelopment.sample.webapp.core.account.AccountCrudService
import de.blackforrestdevelopment.sample.webapp.core.event.api.Event
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventAPI
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventAPI.Companion.PERMISSION_ADMIN
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventAPI.Companion.PERMISSION_MODERATOR
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventAPI.Companion.PERMISSION_READ
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventAPI.Companion.PERMISSION_WRITE
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventChangeRequest
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventInfo
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/event")
@Tag(name = "Event API")
class EventController(
    private val service: EventCrudService,
    private val accountService: AccountCrudService,
) : EventAPI {

    @Get("/{id}")
    override fun get(auth: Authentication, id: Long): Event? {
        return auth.checkPermission(PERMISSION_READ, PERMISSION_ADMIN) {
            service.get(id)
        }
    }

    @Get("/{id}/info")
    override fun getInfo(auth: Authentication, id: Long): EventInfo? {
        return auth.checkPermission(PERMISSION_READ, PERMISSION_ADMIN) {
            val account = accountService.get(auth) ?: return@checkPermission null
            service.getInfo(id, account)
        }
    }

    @Get()
    override fun getAll(auth: Authentication, pageable: Pageable): Page<Event> {
        return auth.checkPermission(PERMISSION_READ, PERMISSION_ADMIN) {
            if (isAdmin(auth)) {
                service.getAll(pageable)
            } else {
                val account = accountService.get(auth) ?: return@checkPermission Page.empty()
                service.getAllForAccount(account, pageable)
            }
        }
    }

    @Get("/info")
    override fun getInfos(auth: Authentication, pageable: Pageable): Page<EventInfo> {
        return auth.checkPermission(PERMISSION_READ, PERMISSION_ADMIN) {
            if (isAdmin(auth)) {
                service.getInfos(pageable)
            } else {
                val account = accountService.get(auth) ?: return@checkPermission Page.empty()
                service.getInfosForAccount(account, pageable)
            }

        }
    }

    @Post()
    override fun create(auth: Authentication, @Body request: EventChangeRequest): Event {
        return auth.checkPermission(PERMISSION_WRITE, PERMISSION_ADMIN) {
            service.create(accountService.find(auth), request)
        }
    }

    @Post("/{ownerId}")
    override fun create(auth: Authentication, ownerId: Long, @Body request: EventChangeRequest): Event {
        return auth.checkPermission(PERMISSION_ADMIN, PERMISSION_MODERATOR) {
            val owner = accountService.get(ownerId)
            require(owner != null) { "Cannot find account for user" }
            service.create(owner, request)
        }
    }

    @Put("/{id}")
    override fun update(auth: Authentication, id: Long, @Body request: EventChangeRequest): Event {
        return auth.checkPermission(PERMISSION_WRITE, PERMISSION_ADMIN) {
            service.update(accountService.find(auth), id, request)
        }
    }

    @Delete("/{id}")
    override fun delete(auth: Authentication, id: Long): Event? {
        return auth.checkPermission(PERMISSION_WRITE, PERMISSION_ADMIN) {
            service.delete(accountService.find(auth), id)
        }
    }

    @Put("/{id}/published")
    override fun setPublished(auth: Authentication, id: Long, @Body value: PatchRequest<Boolean>): Event? {
        return auth.checkPermission(PERMISSION_WRITE, PERMISSION_ADMIN) {
            service.setPublished(accountService.find(auth), id, value)
        }
    }


    private fun isAdmin(auth: Authentication) = auth.getRealmRoles().contains(PERMISSION_ADMIN)

}
