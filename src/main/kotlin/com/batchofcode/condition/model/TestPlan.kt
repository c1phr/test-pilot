package com.batchofcode.condition.model

import com.fasterxml.jackson.annotation.*
import java.util.*
import javax.persistence.*

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Table(name = "test_plan")
@Entity

data class TestPlan(
        @Id
        @Column(name = "plan_id")
        val id: String = UUID.randomUUID().toString(),
        @Column
        val source: String = "",
        @Column
        val version: String = "",

        @OneToMany(mappedBy = "planId", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        @JsonIgnore
        var rules: List<TestRule> = mutableListOf()
) {
    fun planSatisfied(): Boolean = rules.all { it.satisfied }
}