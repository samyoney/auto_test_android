package com.template.autotest_base.tests.screenshot

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.template.autotest_base.framework.BaseTest
import com.template.autotest_base.modelviews.HomeView
import com.template.autotest_base.modelviews.LoginView
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SCLogin : BaseTest() {
    @Test
    fun test_sc_001_login() {
        HomeView {
            this.changeToHome()
            this.goLoginIfNeed()
        }
        LoginView {
            this.captureScreen("Vertical")
            this.backToPreview()
        }
    }
}