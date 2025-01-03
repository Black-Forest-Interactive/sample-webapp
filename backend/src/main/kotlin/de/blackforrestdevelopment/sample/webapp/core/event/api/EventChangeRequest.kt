package de.blackforrestdevelopment.sample.webapp.core.event.api


import de.blackforrestdevelopment.sample.webapp.common.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Serdeable
data class EventChangeRequest(
    val start: LocalDateTime,
    val finish: LocalDateTime,

    val title: String,
    val shortText: String,
    val longText: String,
    val imageUrl: String,
    val iconUrl: String,

    val published: Boolean,
    val tags: Set<String>,
) : BusinessObjectChangeRequest
