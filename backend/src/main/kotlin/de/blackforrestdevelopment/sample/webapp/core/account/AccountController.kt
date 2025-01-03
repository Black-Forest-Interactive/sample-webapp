package de.blackforrestdevelopment.sample.webapp.core.account

import de.blackforrestdevelopment.sample.webapp.common.checkPermission
import de.blackforrestdevelopment.sample.webapp.core.account.api.*
import de.blackforrestdevelopment.sample.webapp.core.account.api.AccountAPI.Companion.PERMISSION_ADMIN
import de.blackforrestdevelopment.sample.webapp.core.account.api.AccountAPI.Companion.PERMISSION_MODERATOR
import de.blackforrestdevelopment.sample.webapp.core.account.api.AccountAPI.Companion.PERMISSION_READ
import de.blackforrestdevelopment.sample.webapp.core.account.api.AccountAPI.Companion.PERMISSION_WRITE
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/account")
@Tag(name = "Account API")
class AccountController(
    private val service: AccountCrudService,
) : AccountAPI {

    @Get("/{id}")
    override fun get(auth: Authentication, id: Long): Account? {
        return auth.checkPermission(PERMISSION_READ) { service.get(id) }
    }

    @Get("/find/by/name")
    override fun findByName(auth: Authentication, @QueryValue name: String, pageable: Pageable): Page<Account> {
        return auth.checkPermission(PERMISSION_READ) { service.findByName(name, pageable) }
    }

    @Get("/find/by/email")
    override fun findByEmail(auth: Authentication, @QueryValue email: String): Account? {
        return auth.checkPermission(PERMISSION_READ) { service.findByEmail(email) }
    }

    @Get("/find/by/externalId")
    override fun findByExternalId(auth: Authentication, @QueryValue externalId: String): Account? {
        return auth.checkPermission(PERMISSION_READ) { service.findByExternalId(externalId) }
    }

    @Get()
    override fun getAll(auth: Authentication, pageable: Pageable): Page<Account> {
        return auth.checkPermission(PERMISSION_READ) { service.getAll(pageable) }
    }

    @Post()
    override fun create(auth: Authentication, @Body request: AccountChangeRequest): Account {
        return auth.checkPermission(PERMISSION_WRITE) { service.create(service.find(auth), request) }
    }

    @Put("/{id}")
    override fun update(auth: Authentication, id: Long, @Body request: AccountChangeRequest): Account {
        return auth.checkPermission(PERMISSION_WRITE) { service.update(service.find(auth), id, request) }
    }

    @Delete("/{id}")
    override fun delete(auth: Authentication, id: Long): Account? {
        return auth.checkPermission(PERMISSION_ADMIN) { service.delete(service.find(auth), id) }
    }

    @Get("/validate")
    override fun validate(auth: Authentication, @QueryValue lang: String): AccountValidationResult {
        return auth.checkPermission(PERMISSION_READ) { service.validate(auth, lang) }
    }

    @Post("/setup")
    override fun setup(auth: Authentication, @Body request: AccountSetupRequest): AccountInfo? {
        return auth.checkPermission(PERMISSION_ADMIN, PERMISSION_MODERATOR) {
            val account = service.get(auth) ?: return@checkPermission null
            service.setup(account, request)
        }
    }

    @Put("/setup/{id}")
    override fun update(auth: Authentication, id: Long, @Body request: AccountSetupRequest): AccountInfo? {
        return auth.checkPermission(PERMISSION_ADMIN, PERMISSION_MODERATOR) {
            val account = service.get(auth) ?: return@checkPermission null
            service.update(account, id, request)
        }
    }

}
