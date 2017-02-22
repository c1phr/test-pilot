package com.batchofcode.observe.repository

import com.batchofcode.observe.model.TestPlan
import org.springframework.data.repository.CrudRepository

/**
 * Created by ryanbatchelder on 2/19/17.
 */
interface TestPlanRepository: CrudRepository<TestPlan, String> {
    fun findFirstBySourceAndVersion(source: String, version: String): List<TestPlan>
}