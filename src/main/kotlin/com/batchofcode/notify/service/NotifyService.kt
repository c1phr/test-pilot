package com.batchofcode.notify.service

import com.batchofcode.condition.model.TestPlan
import org.springframework.stereotype.Component

@Component
class NotifyService constructor(private val bambooDeployer: BambooDeployer) {

    fun notifyPlanSatisfied(plan: TestPlan) {
        print("Test Plan successful: ${plan.id}")
        if (plan.notificationType == "email") {
            // TODO: Send email
        }
        else if (plan.notificationType == "bamboo") {
            bambooDeployer.deploy(plan)
        }
    }
}
