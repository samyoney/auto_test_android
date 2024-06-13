package com.template.autotest_base.framework.utils

import android.R
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.airbnb.lottie.LottieAnimationView
import com.template.autotest_base.framework.Environment
import com.template.autotest_base.framework.extensions.isDisplayed
import org.hamcrest.Matchers

object InterruptedUtils {
    inline fun handleInterruptedException(action: () -> Unit) {
        waitForViewRedrawed()
        try {
            action()
        } catch (e: Throwable) {
            handleInterruptedException()
            action()
        }
    }

    fun handleInterruptedException() {
        closeSystemDialogIfExisting()
        closeDemoNoticeDialogIfExisting()
        closeNoticeDialogIfExisting()
        closeReviewDialogIfExisting()
        closeInAppMessagingViewIfExisting()
        closePriceAlertDialogIfExisting()
    }

    private fun closeSystemDialogIfExisting() {
        val positiveButton = Espresso.onView(ViewMatchers.withId(R.id.button1))
        val negativeButton = Espresso.onView(ViewMatchers.withId(R.id.button2))
        if (positiveButton.isDisplayed()) {
            positiveButton.perform(ViewActions.click())
            waitForIdleState()
        } else if (negativeButton.isDisplayed()) {
            negativeButton.perform(ViewActions.click())
        }
    }

    private fun closeDemoNoticeDialogIfExisting() {
        val titleDemoNoticeDialog = Espresso.onView(ViewMatchers.withText("デモ口座に関するご注意"))
        val noticeDialogConfirmButton = Espresso.onView(ViewMatchers.withText("OK"))
        if (titleDemoNoticeDialog.isDisplayed()) {
            noticeDialogConfirmButton.perform(ViewActions.click())
        }
    }

    private fun closeNoticeDialogIfExisting() {
        val noticeDialogCloseButton = Espresso.onView(ViewMatchers.withText("あとで"))
        val noticeDialogConfirmButton = Espresso.onView(ViewMatchers.withText("確認する"))
        if (noticeDialogCloseButton.isDisplayed() && noticeDialogConfirmButton.isDisplayed()) {
            noticeDialogCloseButton.perform(ViewActions.click())
        }
    }

    private fun closeReviewDialogIfExisting() {
        val dialogReviewRealButton = Espresso.onView(ViewMatchers.withText("Not now"))
        if (dialogReviewRealButton.isDisplayed()) {
            dialogReviewRealButton.perform(ViewActions.click())
        }
    }

    private fun closeInAppMessagingViewIfExisting() {
        val collapseButton =
            Espresso.onView(ViewMatchers.withId(com.google.firebase.inappmessaging.display.R.id.collapse_button))
        if (collapseButton.isDisplayed()) {
            collapseButton.perform(ViewActions.click())
        }
    }

    private fun closePriceAlertDialogIfExisting() {
        val priceDialogTitle = Espresso.onView(ViewMatchers.withText("ご指定のレートに達しました"))
        if (priceDialogTitle.isDisplayed()) {
            Espresso.onView(ViewMatchers.withText("閉じる")).perform(ViewActions.click())
        }
    }

    fun waitForIdleState() {
        waitForViewRedrawed()
        Environment.instrumentation.waitForIdleSync()
        val loading = Espresso.onView(
            MatcherUtils.first(
                Matchers.allOf(
                    ViewMatchers.isAssignableFrom(LottieAnimationView::class.java)
                )
            )
        )
        Helper.waitForPredicate({
            !loading.isDisplayed()
        })
        waitForViewRedrawed()
    }

    fun waitForViewRedrawed(sleepTime: Long = 200) {
        Thread.sleep(sleepTime)
    }
}