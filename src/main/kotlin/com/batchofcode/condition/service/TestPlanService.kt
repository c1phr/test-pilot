package com.batchofcode.condition.service

import com.batchofcode.condition.model.TestPlan
import com.batchofcode.condition.model.TestRule
import com.batchofcode.condition.repository.TestPlanRepository
import com.batchofcode.condition.repository.TestRuleRepository
import org.springframework.stereotype.Service

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Service
class TestPlanService constructor(val testPlanRepository: TestPlanRepository, val testRuleRepository: TestRuleRepository) {
    fun getAll(): MutableIterable<TestPlan>? = testPlanRepository.findAll()

    fun getOne(testPlanId: String): TestPlan? = testPlanRepository.findOne(testPlanId)

    fun save(testPlan: TestPlan): TestPlan {
        return testPlanRepository.save(testPlan)
    }

    fun addRule(testPlanId: String, newRule: TestRule): TestPlan {
        val plan = testPlanRepository.findOne(testPlanId)
        val newRules = plan.rules.toMutableList()
        newRules.add(newRule)
        val newPlan = plan.copy(
                rules = newRules
        )
        return testPlanRepository.save(newPlan)
    }

    fun getBySourceAndVersion(source: String, version: String): TestPlan? = testPlanRepository.findFirstBySourceAndVersion(source, version).getOrNull(0)
}