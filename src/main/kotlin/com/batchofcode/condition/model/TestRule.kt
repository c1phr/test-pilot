package com.batchofcode.condition.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

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
        val count: Int = 0,
        @Column
        var satisfied: Boolean = false,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "test_plan_id")
        var planId: TestPlan? = null
)