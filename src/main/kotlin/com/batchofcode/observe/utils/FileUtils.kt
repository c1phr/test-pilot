package com.batchofcode.observe.utils

import com.batchofcode.observe.model.Event
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import java.io.File

/**
 * Created by ryanbatchelder on 2/20/17.
 */

fun getEventsFromFile(fileName: String, mapper: ObjectMapper): List<Event> {
    val jsonFilePath = fileName.split(":").last()
    val jsonFile = ClassPathResource(jsonFilePath).file
    return jacksonObjectMapper().readValue(jsonFile)
}