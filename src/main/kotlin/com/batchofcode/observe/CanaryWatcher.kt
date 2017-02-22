package com.batchofcode.observe

import com.batchofcode.observe.model.Event
import com.batchofcode.observe.service.EventService
import com.batchofcode.observe.utils.getEventsFromFile
import com.batchofcode.observe.utils.typeRef
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

/**
 * Created by ryanbatchelder on 2/18/17.
 */
@Component
class CanaryWatcher constructor (val eventService: EventService, val restTemplate: RestTemplate) {

    @Value("\${watcher.eventStoreUrls}")
    private lateinit var canaryEventStoreUrl: String

    private val mapper = jacksonObjectMapper()

    @Scheduled(cron = "\${watcher.interval}")
    private fun pollEventStore() {
        val latestTimestamp = eventService.getLatestEvent()?.timestamp ?: 0
        val newEvents: MutableList<Event> = mutableListOf()
        if (canaryEventStoreUrl.contains("local:")) { // Running from local test JSON
            val allEvents = getEventsFromFile(canaryEventStoreUrl, mapper).filter { it.timestamp > latestTimestamp }
            allEvents.forEach { it.source = canaryEventStoreUrl }
            newEvents.addAll(allEvents)
        }
        else {
            for (url in canaryEventStoreUrl.split(",")) { // Allow for multiple remote sources
                val request = RequestEntity<Any>(HttpMethod.GET, URI.create(url))
                val eventResp = restTemplate.exchange(request, typeRef<List<Event>>())
                val allEvents = eventResp?.body ?: listOf()
                allEvents.map { it.source = url }
                newEvents.addAll(allEvents
                        .filter { it.timestamp > latestTimestamp }
                        .map { it.source = url }
                        .filterIsInstance<Event>()
                )
            }
        }

        eventService.save(newEvents)
    }


}