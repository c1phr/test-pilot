package com.batchofcode.observe.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
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
        val id:UUID = UUID.randomUUID(),
        @Column
        @JsonProperty(value = "type")
        val name: String = "",
        @Column
        var source: String = "",
        @Column
        @JsonProperty(value = "appVersion")
        val version: String = "",
        @Column
        var timestamp: Long = 0,
        @Column
        @JsonIgnore
        var count: Int = 1,
        @Column
        var payload: String? = null
)