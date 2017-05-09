package com.batchofcode.notify.service

import com.batchofcode.condition.model.TestPlan
import com.batchofcode.notify.model.NotificationType.BAMBOO
import com.batchofcode.notify.model.NotificationType.EMAIL
import org.springframework.stereotype.Component

@Component
class NotifyService constructor(private val bambooDeployer: BambooDeployer) {

    fun notifyPlanSatisfied(plan: TestPlan) {
        println("Test Plan successful: ${plan.id}")
        if (plan.notificationType == EMAIL) {
            // TODO: Send email
        }
        else if (plan.notificationType == BAMBOO) {
            bambooDeployer.deploy(plan)
        }
    }
}
