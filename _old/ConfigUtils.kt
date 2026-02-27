package org.mgwt.eternity.utils

import io.github.notenoughupdates.moulconfig.common.text.StructuredText

object ConfigUtils {
    fun String.asStructuredText() = StructuredText.of(this)
}