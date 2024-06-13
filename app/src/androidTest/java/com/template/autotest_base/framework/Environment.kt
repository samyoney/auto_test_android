package com.template.autotest_base.framework

import android.app.Instrumentation
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice

object Environment {
    val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
    val context: Context = instrumentation.targetContext
    val device = UiDevice.getInstance(instrumentation)
    var currentFunctionName = String()

    const val TIMEOUT_WAITING = 20000L
    const val TIMEOUT_INTERACTION = 15000L
    const val TIMEOUT_ASSERTION = 0L
}