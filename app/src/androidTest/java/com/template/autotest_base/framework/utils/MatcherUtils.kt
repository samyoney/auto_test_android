package com.template.autotest_base.framework.utils

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.core.internal.deps.guava.base.Predicate
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object MatcherUtils {
    fun first(matcher: Matcher<View>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var isFirst = true
            override fun describeTo(description: Description) {
                description.appendText("Finding the first element in the rootView")
            }

            override fun matchesSafely(item: View): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }
                return false
            }
        }
    }

    fun last(matcher: Matcher<View>): Matcher<View> {
        return getElementFromMatchAtPosition(matcher, getCount(matcher) - 1)
    }

    private fun getElementFromMatchAtPosition(matcher: Matcher<View>, position: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var counter = 0

            override fun describeTo(description: Description) {
                description.appendText("Element at the hierarchical position $position.")
            }

            override fun matchesSafely(item: View): Boolean {
                return matcher.matches(item) && counter++ == position
            }
        }
    }

    private fun getCount(viewMatcher: Matcher<View>, countLimit: Int = 5): Int {
        var actualViewsCount = 0
        do {
            try {
                Espresso.onView(ViewMatchers.isRoot())
                    .check(ViewAssertions.matches(withViewCount(viewMatcher, actualViewsCount)))
                return actualViewsCount
            } catch (ignored: Error) {
            }
            actualViewsCount++
        } while (actualViewsCount < countLimit)
        throw Exception("Counting $viewMatcher failed. The count limit was exceeded.")
    }

    private fun withViewCount(viewMatcher: Matcher<View>, expectedCount: Int): Matcher<View?> {
        return object : TypeSafeMatcher<View?>() {
            var actualCount = -1
            override fun describeTo(description: Description) {
                if (actualCount >= 0) {
                    description.appendText("Expected number of items: $expectedCount")
                    description.appendText("\nMatcher description: ")
                    viewMatcher.describeTo(description)
                    description.appendText("\nActual count: $actualCount")
                }
            }

            override fun matchesSafely(root: View?): Boolean {
                actualCount = 0
                val iterable = TreeIterables.breadthFirstViewTraversal(root)
                actualCount = Iterables.filter(iterable, withMatcherPredicate(viewMatcher)).count()
                return actualCount == expectedCount
            }
        }
    }

    fun withMatcherPredicate(matcher: Matcher<View>): Predicate<View?> {
        return Predicate<View?> { view -> matcher.matches(view) }
    }
}