package de.blackforrestdevelopment.sample.webapp.core.account

import de.blackforrestdevelopment.sample.webapp.common.PatchRequest
import de.blackforrestdevelopment.sample.webapp.common.Storage
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account
import de.blackforrestdevelopment.sample.webapp.core.account.api.AccountChangeRequest
import de.blackforrestdevelopment.sample.webapp.core.account.api.AccountInfo
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface AccountStorage : Storage<Long, Account, AccountChangeRequest> {
    fun findByExternalId(externalId: String): Account?
    fun findByName(name: String, pageable: Pageable): Page<Account>
    fun findByEmail(email: String): Account?
    fun createServiceAccount(request: AccountChangeRequest): Account

    fun getInfos(pageable: Pageable): Page<AccountInfo>
    fun getInfo(account: Account): AccountInfo

    fun setExternalId(id: Long, value: PatchRequest<String>): Account?
}
