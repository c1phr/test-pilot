package com.batchofcode.observe.model

import org.springframework.data.repository.CrudRepository

/**
 * Created by ryanbatchelder on 2/17/17.
 */
interface EventRepository : CrudRepository<Event, String> {
    fun findFirstOrderByTimestampDesc(): List<Event>
    fun findFirstByNameAndBySourceAndByVersion(name: String, source: String, version: String): List<Event>
    fun findBySourceAndByVersion(source: String, version: String): List<Event>
}