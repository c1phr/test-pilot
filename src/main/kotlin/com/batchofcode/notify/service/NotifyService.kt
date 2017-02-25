package com.batchofcode.notify.service

import com.batchofcode.condition.model.TestPlan

/**
 * Created by n0288764 on 2/24/17.
 */

fun notifyPlanSatisfied(plan: TestPlan) {
    print("Test Plan successful: ${plan.id}")
    if (plan.notificationType == "email") {
        // TODO: Send email
    }
    else if (plan.notificationType == "deploy") {
        // TODO: Kick Deployment
    }
}
