package de.blackforrestdevelopment.sample.webapp.common

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class PatchRequest<T>(
    val value: T
)
