package com.batchofcode.observe.controller

import com.batchofcode.observe.model.Event
import com.batchofcode.observe.service.EventService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by ryanbatchelder on 2/18/17.
 */
@RestController
@RequestMapping("/api/event")
class EventController(val eventService: EventService) {
    @RequestMapping("/", method = arrayOf(RequestMethod.GET))
    fun get(@RequestParam(required = false) sourceUrl: String? = null, @RequestParam(required = false) sourceVersion: String? = null): List<Event>? {
        return when {
            sourceUrl.isNullOrEmpty() && sourceVersion.isNullOrEmpty() -> eventService.getAll()
            !sourceUrl.isNullOrEmpty() -> eventService.getSingleAppEvents(sourceUrl!!, sourceVersion)
            else -> null
        }
    }

    @RequestMapping("/", method = arrayOf(RequestMethod.POST))
    fun post(@RequestBody event: Event): Event = eventService.save(event)
}