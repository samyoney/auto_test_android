package com.template.autotest_base.framework.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.template.autotest_base.framework.Environment

@Suppress("unused")
fun Context.getResourceIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}

@Suppress("unused")
fun Context.getResourceString(resourceId: Int): String {
    return getString(resourceId)
}

@Suppress("unused")
fun Context.getResourceBitmap(resourceId: Int): Bitmap {
    return requireNotNull(ContextCompat.getDrawable(Environment.context, resourceId)?.toBitmap())
}
