package com.batchofcode.condition.repository

import com.batchofcode.condition.model.TestRule
import org.springframework.data.repository.CrudRepository

/**
 * Created by ryanbatchelder on 2/19/17.
 */
interface TestRuleRepository: CrudRepository<TestRule, String>