package com.batchofcode.observe.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by ryanbatchelder on 2/17/17.
 */
@Table
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class Event(
        @Id
        @Column
        val id:UUID,
        @Column
        val name: String,
        @Column
        var source: String?,
        @Column
        val version: String,
        @Column
        var timestamp: Int,
        @Column
        @JsonIgnore
        var count: Int = 1
)