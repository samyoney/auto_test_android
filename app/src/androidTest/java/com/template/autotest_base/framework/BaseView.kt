package com.template.autotest_base.framework

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.*
import androidx.test.espresso.matcher.ViewMatchers
import com.template.autotest_base.framework.extensions.*
import com.template.autotest_base.framework.recyclerview.withRecyclerView
import com.template.autotest_base.framework.utils.CaptureImage
import com.template.autotest_base.framework.utils.InterruptedUtils
import com.template.autotest_base.framework.utils.MatcherUtils
import org.hamcrest.Matcher
import org.hamcrest.Matchers

abstract class BaseView<out T : BaseView<T>> {

    private val btnBackPairList = withView("戻る")

    operator fun <R> invoke(block: T.() -> R): R {
        waitForIdleState()
        @Suppress("UNCHECKED_CAST")
        return block(this as T)
    }

    protected fun changeMainTab(tabMenu: ViewInteraction) {
        if (tabMenu.isDisplayed()) {
            tabMenu.click()
        } else {
            if (btnBackPairList.isDisplayed()) {
                btnBackPairList.click()
            }
            tabMenu.click()
        }
        waitForIdleState()
    }

    internal fun withView(id: Int): ViewInteraction {
        onView(Matchers.allOf(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        return onView(
            Matchers.allOf(
                ViewMatchers.withId(id),
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                ViewMatchers.isDisplayingAtLeast(90)
            )
        )
    }

    internal fun withView(text: String): ViewInteraction {
        return onView(
            Matchers.allOf(
                ViewMatchers.withText(text),
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                ViewMatchers.isDisplayingAtLeast(90)
            )
        )
    }

    internal fun withContainTextDisplayed(text: String): ViewInteraction {
        return onView(
            Matchers.allOf(
                ViewMatchers.withSubstring(text),
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                ViewMatchers.isDisplayingAtLeast(90)
            )
        )
    }

    internal fun withContainTextDisplayedWithAlpha(text: String): ViewInteraction {
        return onView(
            Matchers.allOf(
                ViewMatchers.withAlpha(1.0f),
                ViewMatchers.withSubstring(text),
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                ViewMatchers.isDisplayingAtLeast(90)
            )
        )
    }

    internal fun withViewFirstMatch(text: String): ViewInteraction {
        return onView(
            MatcherUtils.first(
                Matchers.allOf(
                    ViewMatchers.withText(text),
                    ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                    ViewMatchers.isDisplayingAtLeast(90)
                )
            )
        )
    }

    internal fun withViewFirstMatch(id: Int): ViewInteraction {
        return onView(
            MatcherUtils.first(
                Matchers.allOf(
                    ViewMatchers.withId(id),
                    ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                    ViewMatchers.isDisplayingAtLeast(90)
                )
            )
        )
    }

    internal fun withViewLastMatch(id: Int): ViewInteraction {
        return onView(
            MatcherUtils.last(
                Matchers.allOf(
                    ViewMatchers.withId(id),
                    ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                    ViewMatchers.isDisplayingAtLeast(90)
                )
            )
        )
    }

    internal fun withViewInParent(childId: Int, parentId: Int): ViewInteraction {
        return onView(
            MatcherUtils.first(
                Matchers.allOf(
                    ViewMatchers.withId(childId),
                    ViewMatchers.withParent(ViewMatchers.withId(parentId))
                )
            )
        )
    }

    fun clickCellWith(vararg conditions: String) {
        val matcherCell = arrayListOf<Matcher<View>>()
        conditions.forEach { matcherCell.add(ViewMatchers.hasDescendant(ViewMatchers.withText(it))) }
        val tableMatcher = withRecyclerView(Matchers.allOf(
            ViewMatchers.isAssignableFrom(RecyclerView::class.java),
            ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            ViewMatchers.isDisplayingAtLeast(90))
        )
        tableMatcher.performItemAt(Matchers.allOf(matcherCell), ViewActions.click())
    }

    internal fun captureScreen(screenName: String = "") {
        waitForIdleState()
        CaptureImage.captureScreen(false, screenName)
    }

    private fun waitForIdleState() {
        InterruptedUtils.waitForIdleState()
    }

    fun closeWebViewIfExisting() {
        waitForIdleState()
        Environment.device.pressBack()
        waitForIdleState()
    }

    internal fun isOrientationPortrait() = Environment.device.isNaturalOrientation

    internal fun scrollToBottom() {
        onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("scrollDown"),
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                ViewMatchers.isDisplayingAtLeast(90)
            )
        ).swipeUpUntilHidden()
    }

    internal fun scrollToTop() {
        onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("scrollUp"),
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                ViewMatchers.isDisplayingAtLeast(90)
            )
        ).swipeUpUntilHidden()
    }

    internal fun forceScrollToBottom() {
        val viewInteractionScrollDown = onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("scrollDown"),
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                ViewMatchers.isDisplayingAtLeast(90)
            )
        )
        while ( viewInteractionScrollDown.isDisplayed()) {
            viewInteractionScrollDown.swipeUpUntilHidden()
        }

    }


    internal fun forceScrollToTop() {
        val viewInteractionScrollUp = onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("scrollUp"),
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                ViewMatchers.isDisplayingAtLeast(90)
            )
        )
        while (viewInteractionScrollUp.isDisplayed()) {
            viewInteractionScrollUp.swipeDownUntilHidden()
        }
    }
}