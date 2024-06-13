package com.template.autotest_base.framework.core.rule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RepeatRule : TestRule {
    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS,
        AnnotationTarget.FUNCTION
    )
    annotation class Repeat(val times: Int)

    private class RepeatStatement(
        private val times: Int,
        private val statement: Statement
    ) : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            for (i in 0 until times) {
                statement.evaluate()
            }
        }
    }

    override fun apply(statement: Statement, description: Description): Statement {
        var result = statement
        val repeat = description.getAnnotation(Repeat::class.java)
        if (repeat != null) {
            val times: Int = repeat.times
            result = RepeatStatement(times, statement)
        }
        return result
    }
}