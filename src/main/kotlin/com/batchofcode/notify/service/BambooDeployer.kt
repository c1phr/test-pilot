package com.batchofcode.notify.service

import com.batchofcode.condition.model.TestPlan
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class BambooDeployer constructor(private val authenticationHelper: AuthenticationHelper) {
    val bambooDeployTriggerEndpoint = "/rest/api/latest/queue/deployment/"

    fun deploy(plan: TestPlan) {
        val deploymentId = plan.notificationTarget
        val deployResponse = authenticationHelper.makeAuthenticatedCall(bambooDeployTriggerEndpoint + "?$deploymentId", HttpMethod.POST, String::class.java)
        print(deployResponse.body)
    }
}