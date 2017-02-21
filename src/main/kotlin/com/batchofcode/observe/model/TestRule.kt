package com.batchofcode.observe.model

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by ryanbatchelder on 2/19/17.
 */
@Table
data class TestRule (
        @Id
        @Column
        val id: String,
        @Column
        val eventName: String,
        @Column
        val count: Int,
        @Column
        var satisfied: Boolean = false
)