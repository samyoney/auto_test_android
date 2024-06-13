package com.template.autotest_base.framework.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.contrib.RecyclerViewActions
import org.hamcrest.Matcher

class RecyclerViewItemMatchingExecutor(
    private val recyclerViewMatcher: Matcher<View>,
    private val itemViewMatcher: Matcher<View>
) : RecyclerViewItemExecutor {
    override fun scrollToItem() {
        RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(itemViewMatcher)
    }

    override fun getItemMatcher(): Matcher<View> {
        return withRecyclerView(recyclerViewMatcher).atItem(itemViewMatcher)
    }

    override fun getItemChildMatcher(childMatcher: Matcher<View>): Matcher<View> {
        return withRecyclerView(recyclerViewMatcher).atItemChild(itemViewMatcher, childMatcher)
    }
}