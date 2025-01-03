package de.blackforrestdevelopment.sample.webapp.core.event.db

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.blackforrestdevelopment.sample.webapp.common.DataObject
import de.blackforrestdevelopment.sample.webapp.core.account.api.Account
import de.blackforrestdevelopment.sample.webapp.core.account.api.AccountInfo
import de.blackforrestdevelopment.sample.webapp.core.event.api.Event
import de.blackforrestdevelopment.sample.webapp.core.event.api.EventChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime


@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "Event")
@Table(name = "event")
data class EventData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column var ownerId: Long,

    @Column var start: LocalDateTime,
    @Column var finish: LocalDateTime,

    @Column var title: String,
    @Column var shortText: String,
    @Column var longText: String,
    @Column var imageUrl: String,
    @Column var iconUrl: String,

    @Column var published: Boolean,

    @Column(name = "tags") var tagsJson: String,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : DataObject {
    companion object {
        private val mapper = ObjectMapper().registerKotlinModule()
        fun create(
            account: Account,
            request: EventChangeRequest,
            timestamp: LocalDateTime
        ): EventData {
            val t = mapper.writeValueAsString(request.tags)
            return EventData(
                0,
                account.id,
                request.start,
                request.finish,
                request.title,
                request.shortText,
                request.longText,
                request.imageUrl,
                request.iconUrl,
                request.published,
                t,
                timestamp
            )
        }
    }

    fun convert(account: AccountInfo): Event {
        return Event(
            id,
            account,
            start,
            finish,
            title,
            shortText,
            longText,
            imageUrl,
            iconUrl,
            published,
            getTags(),
            created,
            updated
        )
    }

    @Transient
    private var tags: Set<String>? = null

    @Transient
    fun getTags(): Set<String> {
        if (tags == null) {
            tags = mapper.readValue<Set<String>>(tagsJson)
        }
        return tags!!
    }

    fun update(request: EventChangeRequest, timestamp: LocalDateTime): EventData {
        val t = mapper.writeValueAsString(request.tags)
        tags = request.tags
        tagsJson = t

        start = request.start
        finish = request.finish
        title = request.title
        shortText = request.shortText
        longText = request.longText
        imageUrl = request.imageUrl
        iconUrl = request.iconUrl
        updated = timestamp
        return this
    }

    fun setPublished(value: Boolean, timestamp: LocalDateTime): EventData {
        this.published = value
        updated = timestamp
        return this
    }
}


