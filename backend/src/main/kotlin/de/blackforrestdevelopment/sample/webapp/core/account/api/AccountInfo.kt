package de.blackforrestdevelopment.sample.webapp.core.account.api

import de.blackforrestdevelopment.sample.webapp.common.BusinessObject
import de.blackforrestdevelopment.sample.webapp.core.account.db.AccountData

data class AccountInfo(
    override val id: Long,
    val name: String,
    val iconUrl: String,
    val email: String,
    val firstName: String,
    val lastName: String
) : BusinessObject<Long> {
    companion object {
        fun create(account: Account): AccountInfo {
            return AccountInfo(
                account.id,
                account.name,
                account.iconUrl,
                account.email ?: "",
                account.firstName ?: "",
                account.lastName ?: ""
            )
        }

        fun create(account: AccountData): AccountInfo {
            return AccountInfo(
                account.id,
                account.name,
                account.iconUrl,
                account.email ?: "",
                account.firstName ?: "",
                account.lastName ?: ""
            )
        }
    }

    fun getTitle(): String {
        return when {
            firstName.isNotBlank() && lastName.isNotBlank() -> "$firstName $lastName"
            else -> name
        }
    }
}
