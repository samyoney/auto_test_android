package com.template.autotest_base.modelviews

import com.template.autotest_base.framework.BaseView
import com.template.autotest_base.framework.extensions.*

val LoginView get() = LoginViewTemp()
class LoginViewTemp : BaseView<LoginViewTemp>() {
    private val btnBack = withView("閉じる")

    fun backToPreview() {
        btnBack.click()
    }

}