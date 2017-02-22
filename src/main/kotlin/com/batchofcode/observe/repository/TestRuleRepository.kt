package com.batchofcode.observe.repository

import com.batchofcode.observe.model.TestRule
import org.springframework.data.repository.CrudRepository

/**
 * Created by ryanbatchelder on 2/19/17.
 */
interface TestRuleRepository: CrudRepository<TestRule, String>