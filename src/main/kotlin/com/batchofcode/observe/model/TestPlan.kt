package com.batchofcode.observe.model

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Table
data class TestPlan (
    @Id
    @Column
    val id: String,
    @Column
    val source: String,
    @Column
    val version: String,
    @Column
    val rules: List<TestRule>
)
{
    fun planSatisfied(): Boolean = rules.all { it.satisfied }
}