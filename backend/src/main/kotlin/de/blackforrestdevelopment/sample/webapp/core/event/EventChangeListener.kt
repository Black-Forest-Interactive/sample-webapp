package de.blackforrestdevelopment.sample.webapp.core.event

import de.blackforrestdevelopment.sample.webapp.common.BusinessObjectChangeListener
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account
import de.blackforrestdevelopment.sample.webapp.core.event.api.Event

interface EventChangeListener : BusinessObjectChangeListener<Long, Event> {
    fun publishedChanged(actor: Account, event: Event)

}
