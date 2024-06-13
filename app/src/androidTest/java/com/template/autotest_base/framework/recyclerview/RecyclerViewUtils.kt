package com.template.autotest_base.framework.recyclerview

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.ArrayList

@Suppress("UNCHECKED_CAST")
internal fun <T : VH, VH : RecyclerView.ViewHolder> itemsMatching(
    recyclerView: RecyclerView, viewHolderMatcher: Matcher<VH>, max: Int
): List<MatchedItem> {
    val adapter = recyclerView.adapter
    val viewHolderCache = SparseArray<VH>()
    val matchedItems = ArrayList<MatchedItem>()
    if (adapter == null) return matchedItems
    for (position in 0 until adapter.itemCount) {
        val itemType = adapter.getItemViewType(position)
        var cachedViewHolder: VH? = viewHolderCache.get(itemType)
        if (cachedViewHolder == null) {
            cachedViewHolder = adapter.createViewHolder(recyclerView, itemType) as? VH
            viewHolderCache.put(itemType, cachedViewHolder)
        }
        adapter.bindViewHolder((cachedViewHolder as? T)!!, position)
        if (viewHolderMatcher.matches(cachedViewHolder)) {
            matchedItems.add(
                MatchedItem(
                    position,
                    HumanReadables.getViewHierarchyErrorMessage(
                        cachedViewHolder.itemView, null,
                        "\n\n*** Matched ViewHolder item at position: $position ***", null
                    )
                )
            )
            if (matchedItems.size == max) {
                break
            }
        }
    }
    return matchedItems
}

internal class MatchedItem(val position: Int, private val description: String) {

    override fun toString(): String {
        return description
    }
}

internal fun <VH : RecyclerView.ViewHolder> viewHolderMatcher(
    itemViewMatcher: Matcher<View>
): Matcher<VH> {
    return object : TypeSafeMatcher<VH>() {
        override fun matchesSafely(item: VH): Boolean {
            return itemViewMatcher.matches(item.itemView)
        }

        override fun describeTo(description: Description) {
            description.appendText("holder with view: ")
            itemViewMatcher.describeTo(description)
        }
    }
}
