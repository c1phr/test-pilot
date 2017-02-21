package com.batchofcode.observe.model

import org.springframework.data.repository.CrudRepository

/**
 * Created by ryanbatchelder on 2/19/17.
 */
interface TestPlanRepository: CrudRepository<TestPlan, String> {
    fun findFirstBySourceAndByVersion(source: String, version: String): List<TestPlan>
}