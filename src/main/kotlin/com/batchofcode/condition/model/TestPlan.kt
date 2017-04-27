package com.batchofcode.condition.model

import java.io.Serializable
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

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
        @Column
        val latestTimestamp: Long = 0,
        @Column
        val notificationType: String = "",
        @Column
        val notificationTarget: String = "",
        @Column
        var completed: Boolean = false,

        @OneToMany(mappedBy = "planId", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        var rules: MutableList<TestRule> = mutableListOf()
): Serializable {
    fun planSatisfied(): Boolean = rules.all { it.satisfied }
}