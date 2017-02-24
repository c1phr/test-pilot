package com.batchofcode.utils

import com.batchofcode.condition.service.TestPlanService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

/**
 * Created by ryanbatchelder on 2/23/17.
 */
@Component
class RuleLoader constructor(private val testPlanService: TestPlanService): ApplicationListener<ApplicationReadyEvent> {

    @Value("classpath:TestPlans/testPlan.json")
    private lateinit var resource: Resource

    override fun onApplicationEvent(event: ApplicationReadyEvent?) {
        val file = resource.file
        val plan = getPlanFromFile(file)
        plan.rules.forEach { it.planId = plan }
        testPlanService.save(plan)
    }
}