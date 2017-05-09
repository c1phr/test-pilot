package com.batchofcode.observe

import com.batchofcode.condition.service.TestPlanService
import com.batchofcode.observe.model.Event
import com.batchofcode.observe.service.EventService
import com.batchofcode.utils.getEventsFromFile
import com.batchofcode.utils.typeRef
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
class CanaryWatcher constructor (val eventService: EventService, val restTemplate: RestTemplate, val testPlanService: TestPlanService) {

    @Value("\${watcher.enabled}")
    private lateinit var watcherEnabled: String

    private val mapper = jacksonObjectMapper()

    @Scheduled(cron = "\${watcher.interval}")
    private fun pollEventStore() {
        if (!watcherEnabled.toBoolean()) {
            return
        }
        val latestTimestamp = eventService.getLatestEvent()?.timestamp ?: 0
        val newEvents: MutableList<Event> = mutableListOf()
        val sourceUrls: List<String>? = eventService.getAll()?.distinctBy { it.source }?.map { it.source }

        sourceUrls?.forEach{ planSource ->
            if (planSource.contains("local:")) { // Running from local test JSON
                val allEvents = getEventsFromFile(planSource, mapper).filter { it.timestamp > latestTimestamp }
                allEvents.forEach { it.source = planSource }
                newEvents.addAll(allEvents)
            }
            else {
                for (url in planSource.split(",")) { // Allow for multiple remote sources
                    val request = RequestEntity<Any>(HttpMethod.GET, URI.create(url))
                    val eventResp = restTemplate.exchange(request, typeRef<List<Event>>()) // Get events from API
                    val allEvents = eventResp?.body ?: listOf()

                    allEvents.forEach{ it.source = url }

                    for (event in allEvents.filter { it.timestamp > latestTimestamp }) {
                        val eventRule = testPlanService.getBySourceAndVersion(event.source.orEmpty(), event.version)
                        if (eventRule != null && (event.timestamp > eventRule.latestTimestamp)) {
                            newEvents.add(event)
                        }
                    }
                }
            }

            eventService.save(newEvents)
        }
    }
}