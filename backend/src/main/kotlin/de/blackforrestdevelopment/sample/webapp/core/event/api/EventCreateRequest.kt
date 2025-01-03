package de.blackforrestdevelopment.sample.webapp.core.event.api

import de.blackforrestdevelopment.sample.webapp.common.BusinessObjectChangeRequest
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account

data class EventCreateRequest(
    val account: Account,
    val content: EventChangeRequest
) : BusinessObjectChangeRequest
