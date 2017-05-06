package com.batchofcode.condition.model

import com.batchofcode.observe.model.Event
import org.junit.Assert.assertEquals
import org.junit.Test

class TestRuleTest {

    private val mockOccurrenceRule = TestRule(
            eventName = "testEvent",
            type = RuleType.OCCURRENCE,
            condition = RuleCondition.EQUALS,
            conditionValue = "1"
    )

    private val mockEvent = Event(
            name = "mockEvent",
            source = "unitTest",
            version = "1",
            timestamp = 12345,
            count = 0
    )

    // Occurrence rule tests

    @Test
    fun shouldNotBeSatisfiedIfEventCountNotSatisfied() {
        assertEquals(false, mockOccurrenceRule.checkRuleIsSatisfied(mockEvent))
    }

    @Test
    fun shouldBeSatisifedIfEventCountSatisfied() {
        val mockEventCountOne = mockEvent.copy(count = 1)
        assertEquals(true, mockOccurrenceRule.checkRuleIsSatisfied(mockEventCountOne))
    }

    @Test
    fun shouldNotBeSatisfiedIfEventCountNotEqual() {
        val mockRuleNotEqual = mockOccurrenceRule.copy(condition = RuleCondition.NOT_EQUALS)
        assertEquals(true, mockRuleNotEqual.checkRuleIsSatisfied(mockEvent))
    }

    @Test
    fun shouldBeSatisfiedIfEventCountNotEqual() {
        val mockRuleNotEqual = mockOccurrenceRule.copy(condition = RuleCondition.NOT_EQUALS)
        val mockEventCountOne = mockEvent.copy(count = 1)
        assertEquals(false, mockRuleNotEqual.checkRuleIsSatisfied(mockEventCountOne))
    }

    @Test
    fun shouldNotBeSatisfiedIfEventCountNotGreater() {
        val mockRuleGreater = mockOccurrenceRule.copy(condition = RuleCondition.GREATER_THAN)
        assertEquals(false, mockRuleGreater.checkRuleIsSatisfied(mockEvent))
    }

    @Test
    fun shouldNotBeSatisfiedIfEventCountEqualNotGreater() {
        val mockRuleGreater = mockOccurrenceRule.copy(condition = RuleCondition.GREATER_THAN)
        val mockEventCountOne = mockEvent.copy(count = 1)
        assertEquals(false, mockRuleGreater.checkRuleIsSatisfied(mockEventCountOne))
    }

    @Test
    fun shouldBeSatisfiedIfEventCountGreater() {
        val mockRuleGreater = mockOccurrenceRule.copy(condition = RuleCondition.GREATER_THAN)
        val mockEventGreater = mockEvent.copy(count = 2)
        assertEquals(true, mockRuleGreater.checkRuleIsSatisfied(mockEventGreater))
    }
}