package com.batchofcode.observe.repository

import com.batchofcode.observe.model.Event
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * Created by ryanbatchelder on 2/17/17.
 */
interface EventRepository : PagingAndSortingRepository<Event, String> {
    fun findFirstByOrderByTimestampDesc(): List<Event>
    fun findFirstByNameAndSourceAndVersion(name: String, source: String, version: String): List<Event>
    fun findBySourceAndVersion(source: String, version: String): List<Event>
}