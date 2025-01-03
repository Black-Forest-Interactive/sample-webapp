package de.blackforrestdevelopment.sample.webapp.core.account

import de.blackforrestdevelopment.sample.webapp.common.BusinessObjectChangeListener
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account

interface AccountChangeListener : BusinessObjectChangeListener<Long, Account>
