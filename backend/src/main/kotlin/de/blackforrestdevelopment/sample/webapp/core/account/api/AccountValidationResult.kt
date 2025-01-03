package de.blackforrestdevelopment.sample.webapp.core.account.api

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AccountValidationResult(
    val created: Boolean,
    val account: Account,
    val info: AccountInfo
)
