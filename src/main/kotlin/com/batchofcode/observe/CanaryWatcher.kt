package com.batchofcode.observe

import com.batchofcode.observe.model.Event
import com.batchofcode.observe.service.EventService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

/**
 * Created by ryanbatchelder on 2/18/17.
 */

class CanaryWatcher constructor (val eventService: EventService, val restTemplate: RestTemplate) {

    @Value("\${watcher.eventStoreUrl}")
    private lateinit var canaryEventStoreUrl: String

    private val mapper = ObjectMapper()

    @Scheduled(cron = "\${watcher.interval}")
    private fun pollEventStore() {
        val latestTimestamp = eventService.getLatestEvent()?.timestamp ?: 0
        val allEvents: List<Event> = restTemplate.getForObject(canaryEventStoreUrl)
        val newEvents = allEvents.filter { it.timestamp > latestTimestamp }

        eventService.save(newEvents)

    }
}