package com.batchofcode.condition

import com.batchofcode.condition.model.TestPlan
import com.batchofcode.condition.service.TestPlanService
import com.batchofcode.notify.service.NotifyService
import com.batchofcode.observe.model.Event
import org.springframework.stereotype.Component

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Component
class RuleValidator constructor(val testPlanService: TestPlanService, val notifyService: NotifyService) {
    fun checkRule(event: Event) {
        val plan = testPlanService.getBySourceAndVersion(event.source.orEmpty(), event.version)
        if (plan != null) {
            val rule = plan.rules.firstOrNull {it.eventName == event.name}
            if (rule != null) {
                if (event.count >= rule.count) {
                    rule.satisfied = true
                    val savedPlan = testPlanService.save(plan) // Save updated plan
                    checkPlan(savedPlan)
                }
            }
        }
    }

    fun checkPlan(plan: TestPlan) {
        if (!plan.completed && plan.planSatisfied()) {
            plan.completed = true
            notifyService.notifyPlanSatisfied(plan)
            testPlanService.save(plan)
        }
    }
}