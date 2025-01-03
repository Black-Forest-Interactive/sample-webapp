package de.blackforrestdevelopment.sample.webapp.core.account


import de.blackforrestdevelopment.sample.webapp.common.*
import de.blackforrestdevelopment.sample.webapp.core.account.api.*
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class AccountCrudService(
    private val storage: AccountStorage,
) : BaseCrudService<Long, Account, AccountChangeRequest, AccountChangeListener>(storage) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountCrudService::class.java)
        private const val SYSTEM_ACCOUNT = "system-account"
    }

    fun findByExternalId(externalId: String): Account? {
        return storage.findByExternalId(externalId)
    }

    fun findByName(name: String, pageable: Pageable): Page<Account> {
        return storage.findByName(name, pageable)
    }

    fun findByEmail(email: String): Account? {
        return storage.findByEmail(email)
    }

    fun validate(auth: Authentication, lang: String): AccountValidationResult {
        val account = findExistingAccount(auth) ?: createNewAccount(auth, lang)
        val info = AccountInfo.create(account)
        return AccountValidationResult(true, account, info)
    }

    private fun findExistingAccount(auth: Authentication) =
        storage.findByExternalId(auth.getExternalId()) ?: storage.findByEmail(auth.getEmail())

    private fun createNewAccount(auth: Authentication, lang: String): Account {
        return create(
            getSystemAccount(),
            AccountChangeRequest(
                auth.getUsername(),
                "",
                auth.getExternalId(),
                null,
                null,
                null,
                auth.getFirstName(),
                auth.getLastName()
            )
        )
    }


    fun get(auth: Authentication): Account? {
        var account = findByExternalId(auth.getExternalId())
        if (account != null) return account

        account = storage.findByEmail(auth.getEmail()) ?: return null
        if (account.externalId == null) {
            account = storage.setExternalId(account.id, PatchRequest(auth.getExternalId()))
        }
        return account
    }

    fun find(auth: Authentication): Account {
        val result = get(auth)
        require(result != null) { "Cannot find account for user" }
        return result
    }

    fun getSystemAccount(): Account {
        val existing = storage.findByExternalId(SYSTEM_ACCOUNT)
        if (existing != null) return existing

        val request = AccountChangeRequest("system", "system", SYSTEM_ACCOUNT, null, null, null, "", "")
        return storage.createServiceAccount(request)
    }

    fun getInfos(pageable: Pageable): Page<AccountInfo> {
        return storage.getInfos(pageable)
    }

    fun getInfo(account: Account): AccountInfo {
        return storage.getInfo(account)
    }

    fun setup(actor: Account, request: AccountSetupRequest): AccountInfo {
        val existing = findExisting(request)
        if (existing != null) return update(actor, existing, request)

        val account = storage.create(request.account, emptyMap())

        notifyCreated(actor, account)

        return AccountInfo.create(account)
    }

    private fun findExisting(request: AccountSetupRequest): Account? {
        if (!request.account.email.isNullOrBlank()) return storage.findByEmail(request.account.email)
        // TODO check that a little more in detail
        return null
    }

    fun update(actor: Account, id: Long, request: AccountSetupRequest): AccountInfo {
        val existing = storage.get(id) ?: return setup(actor, request)

        return update(actor, existing, request)
    }

    private fun update(actor: Account, account: Account, request: AccountSetupRequest): AccountInfo {
        val result = storage.update(account.id, request.account)

        notifyUpdated(actor, result)

        return AccountInfo.create(result)
    }
}
