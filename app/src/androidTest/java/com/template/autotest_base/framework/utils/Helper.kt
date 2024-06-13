package com.template.autotest_base.framework.utils

import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.test.espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.template.autotest_base.framework.Environment
import com.template.autotest_base.framework.Environment.TIMEOUT_WAITING
import com.template.autotest_base.framework.extensions.isAssignedFrom
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher
import java.io.File
import kotlin.random.Random

@Suppress("unused")
object Helper {
    private const val LOG_TAG = "AutoTest"

    fun pushLogInfo(message: String) = Log.i(LOG_TAG, message)
    fun pushLogDebug(message: String) = Log.d(LOG_TAG, message)
    fun pushLogError(message: String) = Log.e(LOG_TAG, message)
    fun pushLogWarn(message: String) = Log.w(LOG_TAG, message)

    fun randomBool() = Random.nextBoolean()

    fun randomInt(from: Int, until: Int) = Random.nextInt(from, until)

    fun randomDouble(from: Double, until: Double, mFormat: String = "%.1f") = formatDouble(Random.nextDouble(from, until), mFormat)

    fun parseDouble(value: String?, defValue: Double = 0.0): Double {
        if (value.isNullOrEmpty()) return defValue
        return try {
            value.trim().toDouble()
        } catch (e: Exception) {
            defValue
        }
    }

    private fun formatDouble(value: Double, mFormat: String = "%.3f") = mFormat.format(value).toDouble()

    inline fun <reified T> randomElementInArray(arr: Array<T>): T {
        return arr[Random.nextInt(arr.size)]
    }

    fun handleViewInteraction(
        viewInteraction: ViewInteraction,
        viewAction: ViewAction,
        timeout: Long
    ): Boolean {
        return handleFunctionCommon(allowedExceptions = mutableListOf(), doIt = {
            viewInteraction.perform(viewAction)
        }, timeout)

    }

    fun handleViewAssertion(viewInteraction: ViewInteraction, matcher: Matcher<View>, timeout: Long): Boolean {
        val allowedExceptions = mutableListOf(
            PerformException::class.java,
            NoMatchingViewException::class.java,
            AssertionFailedError::class.java,
        )
        return handleFunctionCommon(allowedExceptions, doIt = {
            viewInteraction.check(ViewAssertions.matches(matcher))
        }, timeout)
    }

    private fun handleFunctionCommon(allowedExceptions: MutableList<Class<out Throwable>> = mutableListOf(), doIt: () -> Unit, timeout: Long): Boolean {
        InterruptedUtils.waitForViewRedrawed()
        val exceptions: MutableList<Throwable> = mutableListOf()
        var isSuccess = false
        var description = ""
        var exception: Throwable? = null
        val endTime = SystemClock.elapsedRealtime() + timeout

        val block = {
            try {
                isSuccess = true
                doIt()
            } catch (error: Throwable) {
                isSuccess = false
                exception = error
            }
            if (!isSuccess) {
                val error = exception ?: UnknownError()
                if (!error::class.java.isAssignedFrom(allowedExceptions)) {
                    description = error.stackTraceToString().split("View Hierarchy").first()
                    exceptions.add(error)
                }
            }
            if (isSuccess) InterruptedUtils.waitForViewRedrawed()
        }

        if (timeout == 0L) block() else {
            try {
                do {
                    block()
                } while (SystemClock.elapsedRealtime() < endTime && !isSuccess)
            } catch (th: Throwable) {
                isSuccess = false
            }
            if (!isSuccess && exceptions.isNotEmpty()) {
                throw Throwable(message = description)
            }
        }
        return isSuccess
    }

    fun waitForPredicate(predicate: () -> Boolean, timeout: Long = TIMEOUT_WAITING) {
        var isSuccess : Boolean
        val endTime = SystemClock.elapsedRealtime() + timeout
        do {
            isSuccess = predicate()
            if (isSuccess) InterruptedUtils.waitForViewRedrawed()
        } while (SystemClock.elapsedRealtime() < endTime && !isSuccess)
    }

    fun Bitmap.save(name: String) {
        val namePth = name.replace("/","_")
        val path = Environment.context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
        val file = File(path, "$namePth.png")
        file.outputStream().use { stream ->
            compress(Bitmap.CompressFormat.PNG, 100, stream)
        }
    }

    fun handleSwipeUntilCommon(untilVisible: Boolean, viewInteraction: ViewInteraction, scrollFunc: () -> Unit, timeout: Long, duration: Long = 0L) {
        var isSuccess: Boolean
        val endTime = SystemClock.elapsedRealtime() + timeout
        do {
            try {
                try {
                    isSuccess = untilVisible
                    viewInteraction.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                } catch (error: Throwable) {
                    isSuccess = !untilVisible
                }
                if (!isSuccess) scrollFunc()
            } catch (error: Throwable) {
                isSuccess = !untilVisible
                print(error)
            }
            if (isSuccess) InterruptedUtils.waitForViewRedrawed()
            SystemClock.sleep(duration)
        } while (SystemClock.elapsedRealtime() < endTime && !isSuccess)
    }

    fun swipeUpScreen() {
        val scrWidth = Environment.device.displayWidth
        val scrHeight = Environment.device.displayHeight
        Environment.device.swipe((scrWidth*0.5).toInt(), (scrHeight*0.7).toInt(), (scrWidth*0.5).toInt(), (scrHeight*0.5).toInt(), 20)
    }

    fun swipeDownScreen() {
        val scrWidth = Environment.device.displayWidth
        val scrHeight = Environment.device.displayHeight
        Environment.device.swipe((scrWidth*0.5).toInt(), (scrHeight*0.5).toInt(), (scrWidth*0.5).toInt(), (scrHeight*0.7).toInt(), 20)
    }

    fun swipeLeftScreen() {
        val scrWidth = Environment.device.displayWidth
        val scrHeight = Environment.device.displayHeight
        Environment.device.swipe((scrWidth*0.6).toInt(), (scrHeight*0.5).toInt(), (scrWidth*0.1).toInt(), (scrHeight*0.5).toInt(), 20)
    }

    fun swipeRightScreen() {
        val scrWidth = Environment.device.displayWidth
        val scrHeight = Environment.device.displayHeight
        Environment.device.swipe((scrWidth*0.4).toInt(), (scrHeight*0.5).toInt(), (scrWidth*0.6).toInt(), (scrHeight*0.5).toInt(), 20)
    }
}