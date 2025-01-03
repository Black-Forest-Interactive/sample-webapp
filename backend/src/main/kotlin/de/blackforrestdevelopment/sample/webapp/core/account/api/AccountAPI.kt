package de.blackforrestdevelopment.sample.webapp.core.account.api

import de.blackforrestdevelopment.sample.webapp.common.CrudAPI
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication

interface AccountAPI : CrudAPI<Long, Account, AccountChangeRequest> {
    companion object {
        const val PERMISSION_READ = "account.read"
        const val PERMISSION_WRITE = "account.write"
        const val PERMISSION_ADMIN = "account.admin"
        const val PERMISSION_MODERATOR = "account.mod"
    }

    fun findByExternalId(auth: Authentication, externalId: String): Account?
    fun findByName(auth: Authentication, name: String, pageable: Pageable): Page<Account>
    fun findByEmail(auth: Authentication, email: String): Account?
    fun validate(auth: Authentication, lang: String = ""): AccountValidationResult

    fun setup(auth: Authentication, request: AccountSetupRequest): AccountInfo?
    fun update(auth: Authentication, id: Long, request: AccountSetupRequest): AccountInfo?
}
