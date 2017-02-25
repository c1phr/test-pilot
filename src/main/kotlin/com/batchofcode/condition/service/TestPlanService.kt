package com.batchofcode.condition.service

import com.batchofcode.condition.model.TestPlan
import com.batchofcode.condition.repository.TestPlanRepository
import org.springframework.stereotype.Service

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Service
class TestPlanService constructor(val testPlanRepository: TestPlanRepository) {
    fun save(testPlan: TestPlan): TestPlan = testPlanRepository.save(testPlan)

    fun getBySourceAndVersion(source: String, version: String): TestPlan? = testPlanRepository.findFirstBySourceAndVersion(source, version).getOrNull(0)
}