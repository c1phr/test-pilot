package com.batchofcode.observe.service

import com.batchofcode.condition.RuleValidator
import com.batchofcode.observe.model.Event
import com.batchofcode.observe.repository.EventRepository
import org.springframework.stereotype.Service

/**
 * Created by ryanbatchelder on 2/18/17.
 */
@Service
class EventService constructor(val eventRepository: EventRepository, val ruleValidator: RuleValidator) {
    fun save(event: Event) {
        val existingEvent = eventRepository.findFirstByNameAndSourceAndVersion(event.name, event.source.orEmpty(), event.version).getOrNull(0)
        val savedEvent: Event
        if (existingEvent != null) {
            existingEvent.count++
            existingEvent.timestamp = event.timestamp // Update to the latest timestamp
            savedEvent = eventRepository.save(existingEvent)
        }
        else {
            savedEvent = eventRepository.save(event)
        }

        ruleValidator.checkRule(savedEvent)
    }

    fun save(events: List<Event>) {
        events.forEach { save(it) }
    }

    fun getLatestEvent(): Event? {
        return eventRepository.findFirstByOrderByTimestampDesc().getOrNull(0)
    }

    fun getAll(): List<Event>? {
        return eventRepository.findAll().toList()
    }

    fun getSingleAppEvents(sourceUrl: String, sourceVersion: String?): List<Event>? {
        return when {
            sourceVersion.isNullOrEmpty() -> eventRepository.findBySource(sourceUrl)
            else -> eventRepository.findBySourceAndVersion(sourceUrl, sourceVersion!!)
        }
    }
}