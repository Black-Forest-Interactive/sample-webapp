package de.blackforrestdevelopment.sample.webapp.core.account.api

import de.blackforrestdevelopment.sample.webapp.common.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AccountChangeRequest(
    val name: String,
    val iconUrl: String,
    val externalId: String?,
    val email: String? = null,
    val phone: String? = null,
    val mobile: String? = null,

    val firstName: String,
    val lastName: String,
) : BusinessObjectChangeRequest
