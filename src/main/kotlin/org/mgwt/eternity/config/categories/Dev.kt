package org.mgwt.eternity.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class Dev {
    @ConfigOption(name = "Wide MoulConfig", desc = "Make Config Screen wide")
    @ConfigEditorBoolean
    @Expose
    var moulConfigWide = false
}