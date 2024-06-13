package com.template.autotest_base.modelviews

import com.template.autotest_base.framework.BaseView
import com.template.autotest_base.framework.extensions.*

val HomeView = lazy { HomeViewTemp() }.value
class HomeViewTemp : BaseView<HomeViewTemp>() {
    private val tabHome = withView("ホーム")
    private val btnLogin = withView("ログイン")

    fun changeToHome() {
        changeMainTab(tabHome)
    }

    fun goLoginIfNeed() {
        btnLogin.click()
    }
}