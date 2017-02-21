package com.batchofcode.observe

import com.batchofcode.observe.model.Event
import com.batchofcode.observe.service.EventService
import com.batchofcode.observe.service.TestPlanService
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Component
class RuleValidator constructor(val eventService: EventService, val testPlanService: TestPlanService) {
    fun checkRule(event: Event) {
        val plan = testPlanService.getBySourceAndVersion(event.source, event.version)
        if (plan != null) {
            val rule = plan.rules.firstOrNull {it.eventName == event.name}
            if (rule != null) {
                if (event.count >= rule.count) {
                    rule.satisfied = true
                    testPlanService.save(plan) // Save updated plan
                }
            }
        }
    }
}