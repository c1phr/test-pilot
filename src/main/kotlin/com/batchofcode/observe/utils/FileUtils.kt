package com.batchofcode.observe.utils

import com.batchofcode.observe.model.Event
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

/**
 * Created by ryanbatchelder on 2/20/17.
 */

fun getEventsFromFile(fileName: String, mapper: ObjectMapper): List<Event> {
    val jsonFilePath = fileName.replace("local:", "")
    val jsonFile = File(jsonFilePath)
    return jacksonObjectMapper().readValue(jsonFile)
}