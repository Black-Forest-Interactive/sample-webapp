package de.blackforrestdevelopment.sample.webapp.core.account.api

import de.blackforrestdevelopment.sample.webapp.common.BusinessObject
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Serdeable
data class Account(
    override val id: Long,
    val externalId: String?,
    val name: String,
    val iconUrl: String,

    val registrationDate: LocalDateTime,
    val lastLoginDate: LocalDateTime?,

    val email: String?,
    val phone: String?,
    val mobile: String?,

    val firstName: String,
    val lastName: String,

    val serviceAccount: Boolean,
    val idpLinked: Boolean,
) : BusinessObject<Long>
