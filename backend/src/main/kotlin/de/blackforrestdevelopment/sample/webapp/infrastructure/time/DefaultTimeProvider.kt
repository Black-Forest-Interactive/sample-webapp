package de.blackforrestdevelopment.sample.webapp.infrastructure.time

import jakarta.inject.Singleton
import java.time.LocalDateTime
import java.time.ZoneOffset

@Singleton
class DefaultTimeProvider : TimeProvider {
    override fun now(): LocalDateTime {
        return LocalDateTime.now(ZoneOffset.UTC)
    }
}
