package de.blackforrestdevelopment.sample.webapp.core.account.db

import de.blackforrestdevelopment.sample.webapp.common.SimpleDataObject
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account
import de.blackforrestdevelopment.sample.webapp.core.account.api.AccountChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "Account")
@Table(name = "account")
data class AccountData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column var externalId: String?,
    @Column var name: String,
    @Column var iconUrl: String,
    @Column var lastLoginDate: LocalDateTime?,

    @Column var serviceAccount: Boolean,
    @Column var idpLinked: Boolean,

    @Column var email: String?,
    @Column var phone: String?,
    @Column var mobile: String?,

    @Column var firstName: String,
    @Column var lastName: String,

    @Column var lastSync: LocalDateTime,
    @Column var created: LocalDateTime,
    @Column var updated: LocalDateTime? = null
) : SimpleDataObject<Account> {

    companion object {
        fun create(
            request: AccountChangeRequest,
            serviceAccount: Boolean,
            idpLinked: Boolean,
            timestamp: LocalDateTime
        ): AccountData {
            return AccountData(
                0,
                request.externalId,
                request.name,
                request.iconUrl,
                timestamp,
                serviceAccount,
                idpLinked,
                request.email,
                request.phone,
                request.mobile,
                request.firstName,
                request.lastName,
                timestamp,
                timestamp,
            )
        }
    }

    override fun convert(): Account {
        return Account(
            id,
            externalId,
            name,
            iconUrl,
            created,
            lastLoginDate,
            email,
            phone,
            mobile,
            firstName,
            lastName,
            serviceAccount,
            idpLinked
        )
    }

    fun update(request: AccountChangeRequest, timestamp: LocalDateTime): AccountData {
        name = request.name
        iconUrl = request.iconUrl
        email = request.email
        phone = request.phone
        mobile = request.mobile
        firstName = request.firstName
        lastName = request.lastName
        updated = timestamp
        return this
    }

    fun login(timestamp: LocalDateTime): AccountData {
        lastLoginDate = timestamp
        return this
    }

    fun setExternalId(externalId: String, timestamp: LocalDateTime): AccountData {
        this.externalId = externalId
        this.updated = timestamp
        return this
    }
}
