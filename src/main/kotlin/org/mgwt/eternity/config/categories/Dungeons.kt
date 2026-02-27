package org.mgwt.eternity.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorKeybind
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class Dungeons {
    @Expose
    @ConfigOption(name = "P4 bb helper", desc = "Highlight blocks to be broken in p4 pad")
    @ConfigEditorBoolean
    var enableP4bbHelper: Boolean = false

    @Expose
    @ConfigOption(name = "Create GhostBlock", desc = "Make a ghost block")
    @ConfigEditorKeybind(defaultKey = 86) // V
    var ghostBlockKeybind = 86
}