package com.batchofcode.observe.controller

import com.batchofcode.observe.model.Event
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by ryanbatchelder on 2/18/17.
 */
@RestController
@RequestMapping("/event")
class EventController {
    @RequestMapping("/", method = arrayOf(RequestMethod.POST))
    fun post(event: Event) {

    }
}