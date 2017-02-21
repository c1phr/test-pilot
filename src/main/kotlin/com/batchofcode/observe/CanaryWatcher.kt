package com.batchofcode.observe

import com.batchofcode.observe.model.Event
import com.batchofcode.observe.service.EventService
import com.batchofcode.observe.utils.getEventsFromFile
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

/**
 * Created by ryanbatchelder on 2/18/17.
 */
@Component
class CanaryWatcher constructor (val eventService: EventService, val restTemplate: RestTemplate) {

    @Value("\${watcher.eventStoreUrl}")
    private lateinit var canaryEventStoreUrl: String

    private val mapper = jacksonObjectMapper()

    @Scheduled(cron = "\${watcher.interval}")
    private fun pollEventStore() {
        val latestTimestamp = eventService.getLatestEvent()?.timestamp ?: 0
        val newEvents: MutableList<Event> = mutableListOf()
        if (canaryEventStoreUrl.contains("local:")) { // Running from local test JSON
            val allEvents = getEventsFromFile(canaryEventStoreUrl, mapper)
            newEvents.addAll(allEvents
                    .filter { it.timestamp > latestTimestamp }
                    .map { it.source = canaryEventStoreUrl }
                    .filterIsInstance<Event>()
            )
        }
        else {
            for (url in canaryEventStoreUrl.split(",")) { // Allow for multiple remote sources
//                val allEvents: List<Event> = restTemplate.getForObject(url)
//                allEvents.map { it.source = url }
//                newEvents.addAll(allEvents
//                        .filter { it.timestamp > latestTimestamp }
//                        .map { it.source = url }
//                        .filterIsInstance<Event>()
//                )
            }
        }

        eventService.save(newEvents)
    }


}