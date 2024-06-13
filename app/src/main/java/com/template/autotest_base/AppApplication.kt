package com.template.autotest_base

import android.app.Application

class AppApplication: Application() {
    companion object {
        val idlingResource = SimpleIdlingResource()
    }
}