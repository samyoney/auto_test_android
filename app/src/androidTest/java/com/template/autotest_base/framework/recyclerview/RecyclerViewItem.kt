package com.template.autotest_base.framework.recyclerview

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import com.template.autotest_base.framework.extensions.*
import org.hamcrest.Matcher

@Suppress("unused")
open class RecyclerViewItem {
    private var executor: RecyclerViewItemExecutor

    constructor(
        recyclerViewMatcher: Matcher<View>,
        itemViewMatcher: Matcher<View>,
        autoScroll: Boolean = true
    ) {
        executor = RecyclerViewItemMatchingExecutor(recyclerViewMatcher, itemViewMatcher)
        if (autoScroll) {
            scrollToItem()
        }
    }

    constructor(recyclerViewMatcher: Matcher<View>, position: Int, autoScroll: Boolean = true) {
        executor = RecyclerViewItemPositionalExecutor(recyclerViewMatcher, position)
        if (autoScroll) {
            scrollToItem()
        }
    }

    private fun scrollToItem(): RecyclerViewItem = apply {
        executor.scrollToItem()
    }

    private fun get(): Matcher<View> {
        return executor.getItemMatcher()
    }

    fun getChildMatcher(childMatcher: Matcher<View>): Matcher<View> {
        return executor.getItemChildMatcher(childMatcher)
    }

    fun click() = apply { onView(this.get()).click() }
    fun longClick() = apply { onView(this.get()).longClick() }
    fun doubleClick() = apply { onView(this.get()).doubleClick() }
    fun swipeDown() = apply { onView(this.get()).swipeDown() }
    fun swipeLeft() = apply { onView(this.get()).swipeLeft() }
    fun swipeRight() = apply { onView(this.get()).swipeRight() }
    fun swipeUp() = apply { onView(this.get()).swipeUp() }
    fun execute(action: ViewAction) = apply { onView(this.get()).execute(action) }

    //assertions
    fun isDisplayed() = apply { onView(this.get()).isDisplayed() }
    fun isCompletelyDisplayed() = apply { onView(this.get()).isCompletelyDisplayed() }
    fun isDisplayingAtLeast(percentage: Int) = apply { onView(this.get()).isDisplayingAtLeast(percentage) }
    fun isClickable() = apply { onView(this.get()).isClickable() }
    fun assertMatches(condition: Matcher<View>) = apply { onView(this.get()).assertMatches(condition) }

}