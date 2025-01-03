package de.blackforrestdevelopment.sample.webapp.core.event.api

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class EventInfo(
    val event: Event,
    val canEdit: Boolean
)