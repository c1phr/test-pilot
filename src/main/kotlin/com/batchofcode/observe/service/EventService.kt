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
        if (existingEvent != null) {
            existingEvent.count++
            existingEvent.timestamp = event.timestamp // Update to the latest timestamp
            eventRepository.save(existingEvent)
        }
        else {
            eventRepository.save(event)
        }

        ruleValidator.checkRule(event)
    }

    fun save(events: List<Event>) {
        events.forEach { save(it) }
    }

    fun getLatestEvent(): Event? {
        return eventRepository.findFirstByOrderByTimestampDesc().getOrNull(0)
    }
}