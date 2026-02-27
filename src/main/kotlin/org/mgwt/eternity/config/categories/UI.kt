package org.mgwt.eternity.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Accordion
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class UI {
    @ConfigOption(name = "Item in hand", desc = "Changes look of item in main hand.")
    @Accordion
    @Expose
    var itemInHandSettings: ItemInHandSettings = ItemInHandSettings()

    class ItemInHandSettings {
        @Expose
        @ConfigOption(name = "Enable small items", desc = "Changes size of item")
        @ConfigEditorBoolean
        var enableSmallItems: Boolean = false

        @Expose
        @ConfigOption(name = "Size of item", desc = "Set size of item")
        @ConfigEditorSlider(minValue = 0.1F, maxValue = 0.9F, minStep = 0.1F)
        var sizeOfItem = 1.0F

        @Expose
        @ConfigOption(name = "Lift offset", desc = "Bigger = higher item in hand")
        @ConfigEditorSlider(minValue = 0.0F, maxValue = 1.0F, minStep = 0.1F)
        var liftOffset = 0.0F

        @ConfigOption(name = "Disable equip animation", desc = "Disable fully equip animation.")
        @ConfigEditorBoolean
        @Expose
        var disableEquip = false
    }
}