package de.blackforrestdevelopment.sample.webapp.common

import de.blackforrestdevelopment.sample.webapp.core.account.api.Account

interface BusinessObjectChangeListener<T, O : BusinessObject<T>> {
    fun handleCreated(actor: Account, obj: O) {
        // intentionally left empty
    }

    fun handleUpdated(actor: Account, obj: O) {
        // intentionally left empty
    }

    fun handleDeleted(actor: Account, obj: O) {
        // intentionally left empty
    }
}
