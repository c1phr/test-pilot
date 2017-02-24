package com.batchofcode.condition.repository

import com.batchofcode.condition.model.TestPlan
import org.springframework.data.repository.CrudRepository

/**
 * Created by ryanbatchelder on 2/19/17.
 */
interface TestPlanRepository: CrudRepository<TestPlan, String> {
    fun findFirstBySourceAndVersion(source: String, version: String): List<TestPlan>
}