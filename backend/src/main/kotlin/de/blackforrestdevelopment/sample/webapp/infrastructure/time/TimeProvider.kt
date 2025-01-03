package de.blackforrestdevelopment.sample.webapp.infrastructure.time

import java.time.LocalDateTime

fun interface TimeProvider {
    fun now(): LocalDateTime
}
