package com.template.autotest_base.framework

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.template.autotest_base.AppApplication
import com.template.autotest_base.MainActivity
import com.template.autotest_base.framework.core.rule.RepeatRule
import com.template.autotest_base.framework.core.rule.RuleSequence
import com.template.autotest_base.framework.core.setup.SetUpTearDownRule
import com.template.autotest_base.framework.utils.CaptureImage
import com.template.autotest_base.framework.utils.Helper
import org.junit.Rule
import org.junit.rules.TestName
import org.junit.rules.TestWatcher
import org.junit.runner.Description

abstract class BaseTest {

    companion object {
        private const val RULE_TEST = "RULE_TEST"
    }

    private val mIdlingResource = AppApplication.idlingResource

    private val setupRule = SetUpTearDownRule()

    @get:Rule
    val mRepeatRule = RepeatRule()

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val ruleSequence = RuleSequence(setupRule)

    @get:Rule
    var nameRule = TestName()

    init {
        setupRule
            .addSetUp {
                IdlingRegistry.getInstance().register(mIdlingResource)
                Environment.currentFunctionName = nameRule.methodName
                onSetUp()
            }
            .addTearDown {
                Helper.pushLogDebug(RULE_TEST)
                IdlingRegistry.getInstance().unregister(mIdlingResource)
                onTearDown()
            }
        ruleSequence.addLast(object : TestWatcher() {
            override fun failed(e: Throwable?, description: Description?) {
                super.failed(e, description)
                CaptureImage.captureScreen(true)
            }
        })
    }

    open fun onSetUp() {
    }

    open fun onTearDown() {

    }
}