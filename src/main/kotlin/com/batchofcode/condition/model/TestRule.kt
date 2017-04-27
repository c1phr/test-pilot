package com.batchofcode.condition.model

import com.batchofcode.condition.model.RuleCondition.*
import com.batchofcode.condition.model.RuleType.OCCURRENCE
import com.batchofcode.condition.model.RuleType.PAYLOAD
import com.batchofcode.observe.model.Event
import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Table(name = "test_rule")
@Entity
data class TestRule (
        @Id
        @Column
        val id: String = UUID.randomUUID().toString(),
        @Column
        val eventName: String = "",
        @Column
        val type: RuleType = OCCURRENCE,
        @Column(nullable = true)
        val condition: RuleCondition = EQUALS,
        @Column(nullable = true)
        val conditionValue: String = "0",
        @Column
        var satisfied: Boolean = false,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "test_plan_id")
        @JsonIgnore
        var planId: TestPlan? = null
): Serializable {
    override fun toString(): String {
            return this.id
    }

    fun checkRuleIsSatisfied(event: Event): Boolean {
        if (this.type == OCCURRENCE) {
            val ruleCount = this.conditionValue.toInt()
            return when (this.condition) {
                EQUALS -> event.count == ruleCount
                NOT_EQUALS -> event.count != ruleCount
                GREATER_THAN -> event.count > ruleCount
                GREATER_THAN_EQUAL -> event.count >= ruleCount
                LESS_THAN -> event.count < ruleCount
                LESS_THAN_EQUAL -> event.count <= ruleCount
                else -> false
            }
        }
        else if (this.type == PAYLOAD && event.payload != null) {
            return when (this.condition) {
                EQUALS -> event.payload == this.conditionValue
                NOT_EQUALS -> event.payload != this.conditionValue
                CONTAINS -> this.conditionValue in event.payload!!
                NOT_CONTAINS -> this.conditionValue !in event.payload!!
                else -> false
            }
        }
        return false
    }
}