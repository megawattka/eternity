package org.mgwt.eternity.utils

import net.minecraft.util.Util

object OsUtils {
    @JvmStatic
    fun openBrowser(url: String) {
        Util.getOperatingSystem().open(url)
    }
}