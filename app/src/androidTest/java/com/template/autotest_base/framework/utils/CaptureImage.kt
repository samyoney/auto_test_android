package com.template.autotest_base.framework.utils

import android.graphics.Bitmap
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import com.template.autotest_base.framework.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


object CaptureImage {
    fun captureScreen(isTestFail: Boolean = false, pathName: String = String()) {
        val namePth = if (!isTestFail) {
            Environment.currentFunctionName + if (pathName.isNotEmpty()) {
                "_" + pathName.replace("/", "_")
            } else {
                pathName
            }
        } else {
            "Failure_" + Environment.currentFunctionName + pathName
        }
        val currentTime = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val path = Environment.context.getExternalFilesDir(currentTime)
        val file = File(path, "$namePth.png")
        Espresso.onView(ViewMatchers.isRoot()).check { view, _ ->
            if (view != null) {
                val contentBitmap = Environment.instrumentation.uiAutomation.takeScreenshot()
                val heightStatusBar = view.resources.getDimensionPixelSize(view.resources.getIdentifier("status_bar_height", "dimen", "android"))
                val heightScreen = contentBitmap.height
                val croppedBitmap = Bitmap.createBitmap(contentBitmap, 0, heightStatusBar, contentBitmap.width, heightScreen.minus(heightStatusBar))
                croppedBitmap.saveImage(file)
            } else {
                Environment.device.takeScreenshot(file)
            }
        }
    }

    private fun Bitmap.saveImage(file: File) {
        file.outputStream().use { stream ->
            compress(Bitmap.CompressFormat.PNG, 100, stream)
        }
    }
}
