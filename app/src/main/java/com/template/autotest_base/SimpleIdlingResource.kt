package com.template.autotest_base

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.Volatile

class SimpleIdlingResource : IdlingResource {
    @Volatile
    private var mCallback: ResourceCallback? = null
    private val mIsIdleNow = AtomicBoolean(true)

    override fun getName(): String {
        return this.javaClass.getName()
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback) {
        mCallback = callback
    }

    fun setIdleState(isIdleNow: Boolean) {
        mIsIdleNow.set(isIdleNow)
        if (isIdleNow && mCallback != null) {
            mCallback?.onTransitionToIdle()
        }
    }
}
