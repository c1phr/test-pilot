package com.batchofcode.condition.service

import com.batchofcode.condition.model.TestPlan
import com.batchofcode.condition.model.TestRule
import com.batchofcode.condition.repository.TestPlanRepository
import com.batchofcode.condition.repository.TestRuleRepository
import com.batchofcode.observe.repository.EventRepository
import org.springframework.stereotype.Service

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Service
class TestPlanService constructor(val testPlanRepository: TestPlanRepository, val testRuleRepository: TestRuleRepository, val eventRepository: EventRepository) {
    fun getAll(): MutableIterable<TestPlan>? = testPlanRepository.findAll()

    fun getAllWithRuleCounts(): List<Map<String, Any>> {
        val allPlans = getAll()
        return allPlans?.map { getTestWithRuleCounts(it) } ?: listOf()
    }

    fun getOne(testPlanId: String): TestPlan? = testPlanRepository.findOne(testPlanId)

    fun getTestWithRuleCounts(testPlan: TestPlan): Map<String, Any> {
        val eventCounts = getAllEventCounts(testPlan)
        return mapOf(Pair("plan", testPlan), Pair("events", eventCounts))
    }

    fun save(testPlan: TestPlan): TestPlan {
        return testPlanRepository.save(testPlan)
    }

    fun addRule(testPlanId: String, newRule: TestRule): TestPlan {
        val plan = testPlanRepository.findOne(testPlanId)
        newRule.planId = plan
        plan.rules.add(newRule)
        return testPlanRepository.save(plan)
    }

    fun removeRule(planId: String, ruleId: String): TestPlan {
        val plan = testPlanRepository.findOne(planId)
        val newPlan = plan.copy(
                rules = plan.rules.filter { it.id != ruleId }.toMutableList()
        )
        val savedPlan = testPlanRepository.save(newPlan)
        testRuleRepository.delete(ruleId)
        return savedPlan
    }

    fun getBySourceAndVersion(source: String, version: String): TestPlan? = testPlanRepository.findFirstBySourceAndVersion(source, version).getOrNull(0)

    private fun getAllEventCounts(plan: TestPlan): Map<String, Int> {
        val countMap = mutableMapOf<String, Int>()
        val planEvents = eventRepository.findBySourceAndVersion(plan.source, plan.version)
        plan.rules.forEach { it ->
            val eventCount = planEvents?.firstOrNull{ event -> event.name == it.eventName }?.count ?: 0
            countMap.put(it.eventName, eventCount)
        }
        return countMap
    }
}