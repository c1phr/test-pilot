package com.batchofcode.observe.model

import javax.persistence.*

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Table (name = "test_plan")
@Entity
data class TestPlan (
    @Id
    @Column(name = "plan_id")
    val id: String,
    @Column
    val source: String,
    @Column
    val version: String,

    @OneToMany(mappedBy = "planId", cascade = arrayOf(CascadeType.ALL))
    val rules: List<TestRule>
)
{
    fun planSatisfied(): Boolean = rules.all { it.satisfied }
}