package com.template.autotest_base.framework.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.*
import androidx.test.espresso.matcher.ViewMatchers
import com.template.autotest_base.framework.Environment
import com.template.autotest_base.framework.Environment.TIMEOUT_ASSERTION
import com.template.autotest_base.framework.Environment.TIMEOUT_INTERACTION
import com.template.autotest_base.framework.utils.Helper
import com.template.autotest_base.framework.utils.InterruptedUtils
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

@Suppress("unused")
fun ViewInteraction.click(
    timeout: Long = TIMEOUT_INTERACTION
) = InterruptedUtils.handleInterruptedException {
    Helper.handleViewInteraction(this, ViewActions.click(), timeout)
}

@Suppress("unused")
fun ViewInteraction.doubleClick(
    timeout: Long = TIMEOUT_INTERACTION
) = InterruptedUtils.handleInterruptedException {
    Helper.handleViewInteraction(this, ViewActions.doubleClick(), timeout)
}

@Suppress("unused")
fun ViewInteraction.longClick(
    timeout: Long = TIMEOUT_INTERACTION
) = InterruptedUtils.handleInterruptedException {
    Helper.handleViewInteraction(this, ViewActions.longClick(), timeout)
}

@Suppress("unused")
fun ViewInteraction.typeText(
    text: String,
    timeout: Long = TIMEOUT_INTERACTION
) = InterruptedUtils.handleInterruptedException {
    Helper.handleViewInteraction(this, ViewActions.typeText(text), timeout)
}

@Suppress("unused")
fun ViewInteraction.replaceText(
    text: String,
    timeout: Long = TIMEOUT_INTERACTION
) = InterruptedUtils.handleInterruptedException {
    Helper.handleViewInteraction(this, ViewActions.replaceText(text), timeout)
}

@Suppress("unused")
fun ViewInteraction.clearText(
    timeout: Long = TIMEOUT_INTERACTION
) = InterruptedUtils.handleInterruptedException {
    Helper.handleViewInteraction(this, ViewActions.clearText(), timeout)
}

@Suppress("unused")
fun ViewInteraction.closeSoftKeyboard(
    timeout: Long = TIMEOUT_INTERACTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewInteraction(this, ViewActions.closeSoftKeyboard(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.swipeUpUntilDisplayed(
    timeout: Long = TIMEOUT_INTERACTION,
    duration: Long = 0L
) {
    Helper.handleSwipeUntilCommon(
        untilVisible = true, this,
        { Helper.swipeUpScreen() },
        timeout, duration
    )
}

@Suppress("unused")
fun ViewInteraction.swipeUpUntilHidden(timeout: Long = TIMEOUT_INTERACTION, duration: Long = 0L) {
    Helper.handleSwipeUntilCommon(
        untilVisible = false, this,
        { Helper.swipeUpScreen() },
        timeout, duration
    )
}

@Suppress("unused")
fun ViewInteraction.swipeLeftUntilDisable(timeout: Long = TIMEOUT_INTERACTION) {
    Helper.handleSwipeUntilCommon(
        untilVisible = true, this,
        { Helper.swipeLeftScreen() },
        timeout
    )
}

@Suppress("unused")
fun ViewInteraction.swipeRightUntilDisable(timeout: Long = TIMEOUT_INTERACTION) {
    Helper.handleSwipeUntilCommon(
        untilVisible = true, this,
        { Helper.swipeRightScreen() },
        timeout
    )
}

@Suppress("unused")
fun ViewInteraction.swipeDownUntilHidden(timeout: Long = TIMEOUT_INTERACTION) {
    Helper.handleSwipeUntilCommon(
        untilVisible = false, this,
        { Helper.swipeDownScreen() },
        timeout
    )
}

@Suppress("unused")
fun ViewInteraction.swipeDownUntilDisplayed(timeout: Long = TIMEOUT_INTERACTION) {
    Helper.handleSwipeUntilCommon(
        untilVisible = true, this,
        { Helper.swipeDownScreen() },
        timeout
    )
}

@Suppress("unused")
fun ViewInteraction.swipeLeftUntilDisplayed(
    timeout: Long = TIMEOUT_INTERACTION,
    duration: Long = 0L
) {
    Helper.handleSwipeUntilCommon(
        untilVisible = true, this,
        { Helper.swipeLeftScreen() },
        timeout, duration
    )
}

@Suppress("unused")
fun ViewInteraction.clickStatusCheckbox(needCheck: Boolean) {
    if (needCheck != isChecked()) click()
}

@Suppress("unused")
fun ViewInteraction.swipeLeft(
    timeout: Long = TIMEOUT_INTERACTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewInteraction(this, ViewActions.swipeLeft(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.swipeRight(
    timeout: Long = TIMEOUT_INTERACTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewInteraction(this, ViewActions.swipeRight(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.swipeUp(
    timeout: Long = TIMEOUT_INTERACTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewInteraction(this, ViewActions.swipeUp(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.swipeDown(
    timeout: Long = TIMEOUT_INTERACTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewInteraction(this, ViewActions.swipeDown(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.scrollTo(
    timeout: Long = TIMEOUT_INTERACTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewInteraction(this, ViewActions.scrollTo(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.execute(
    viewAction: ViewAction,
    timeout: Long = TIMEOUT_INTERACTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) {
    val isSuccess = Helper.handleViewInteraction(this, viewAction, timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.isDisplayed(
    timeout: Long = TIMEOUT_ASSERTION
): Boolean {
    return Helper.handleViewAssertion(this, ViewMatchers.isDisplayed(), timeout)
}

@Suppress("unused")
fun ViewInteraction.isCompletelyDisplayed(
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(this, ViewMatchers.isCompletelyDisplayed(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.isDisplayingAtLeast(
    percentage: Int,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess =
        Helper.handleViewAssertion(this, ViewMatchers.isDisplayingAtLeast(percentage), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.isEnabled(
    timeout: Long = TIMEOUT_ASSERTION
): Boolean {
    return Helper.handleViewAssertion(this, ViewMatchers.isEnabled(), timeout)
}

@Suppress("unused")
fun ViewInteraction.isDisabled(
    timeout: Long = TIMEOUT_ASSERTION,
): Boolean {
    return Helper.handleViewAssertion(this, Matchers.not(ViewMatchers.isEnabled()), timeout)
}

@Suppress("unused")
fun ViewInteraction.isSelected(
    timeout: Long = TIMEOUT_ASSERTION,
): Boolean {
    return Helper.handleViewAssertion(this, ViewMatchers.isSelected(), timeout)
}

@Suppress("unused")
fun ViewInteraction.isClickable(
    timeout: Long = TIMEOUT_ASSERTION
): Boolean {
    return Helper.handleViewAssertion(this, ViewMatchers.isClickable(), timeout)
}

@Suppress("unused")
fun ViewInteraction.isChecked(
    timeout: Long = TIMEOUT_ASSERTION,
): Boolean {
    return Helper.handleViewAssertion(this, ViewMatchers.isChecked(), timeout)
}

@Suppress("unused")
fun ViewInteraction.isFocusable(
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(this, ViewMatchers.isFocusable(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.isNotFocusable(
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess =
        Helper.handleViewAssertion(this, Matchers.not(ViewMatchers.isFocusable()), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.hasFocus(
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(this, ViewMatchers.hasFocus(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.isJavascriptEnabled(
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(this, ViewMatchers.isJavascriptEnabled(), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.hasText(
    text: String,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(this, ViewMatchers.withText(text), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.hasText(
    resourceId: Int,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(this, ViewMatchers.withText(resourceId), timeout)
    resultHandler(isSuccess)
}


@Suppress("unused")
fun ViewInteraction.hasText(
    stringMatcher: Matcher<String>,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(this, ViewMatchers.withText(stringMatcher), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.containsText(
    text: String,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(
        this,
        ViewMatchers.withText(Matchers.containsString(text)),
        timeout
    )
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.hasContentDescription(
    text: String,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess =
        Helper.handleViewAssertion(this, ViewMatchers.withContentDescription(text), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.hasContentDescription(
    resourceId: Int,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess =
        Helper.handleViewAssertion(this, ViewMatchers.withContentDescription(resourceId), timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.hasContentDescription(
    charSequenceMatcher: Matcher<CharSequence>,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(
        this,
        ViewMatchers.withContentDescription(charSequenceMatcher),
        timeout
    )
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.contentDescriptionContains(
    text: String,
    timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(
        this,
        ViewMatchers.withContentDescription(Matchers.containsString(text)),
        timeout
    )
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.assertMatches(
    condition: Matcher<View>, timeout: Long = TIMEOUT_ASSERTION,
    resultHandler: (isSuccess: Boolean) -> Unit = { }
) = apply {
    val isSuccess = Helper.handleViewAssertion(this, condition, timeout)
    resultHandler(isSuccess)
}

@Suppress("unused")
fun ViewInteraction.containsDrawable(
    @DrawableRes id: Int,
    timeout: Long = TIMEOUT_ASSERTION
): Boolean {
    val isSuccess = Helper.handleViewAssertion(this,
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("perform funtion containsDrawable ${Environment.currentFunctionName}")
            }

            override fun matchesSafely(view: View): Boolean {
                val drawable = view.context.getDrawable(id) ?: return false
                val expectedBitmap = drawable.toBitmap()
                return try {
                    view.background.toBitmap().sameAs(expectedBitmap)
                } catch (e: IllegalArgumentException) {
                    if (view is ImageView) {
                        view.drawable.toBitmap().sameAs(expectedBitmap)
                    } else {
                        false
                    }
                }
            }
        }, timeout)
    return isSuccess
}

@Suppress("unused")
fun ViewInteraction.getText(): String {
    var text = String()
    InterruptedUtils.handleInterruptedException {
        this.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return getViewMatcher()
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })
    }
    return text
}

@Suppress("unused")
fun ViewInteraction.isEnabledWithAlpha(
    timeout: Long = TIMEOUT_ASSERTION,
): Boolean {
    return Helper.handleViewAssertion(this, ViewMatchers.withAlpha(1.0f), timeout)
}


