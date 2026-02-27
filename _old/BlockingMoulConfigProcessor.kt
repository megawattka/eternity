package org.mgwt.eternity.config

import io.github.notenoughupdates.moulconfig.annotations.ConfigOption
import io.github.notenoughupdates.moulconfig.gui.GuiOptionEditor
import io.github.notenoughupdates.moulconfig.gui.editors.GuiOptionEditorKeybind
import io.github.notenoughupdates.moulconfig.processor.MoulConfigProcessor
import io.github.notenoughupdates.moulconfig.processor.ProcessedOption
import io.github.notenoughupdates.moulconfig.processor.ProcessedOptionImpl
import org.mgwt.eternity.Eternity
import java.lang.reflect.Field

class BlockingMoulConfigProcessor : MoulConfigProcessor<Features>(Eternity.features) {
    override fun createOptionGui(
        processedOption: ProcessedOption,
        field: Field,
        option: ConfigOption,
    ): GuiOptionEditor? {
        val default = super.createOptionGui(processedOption, field, option) ?: return null
        if (processedOption !is ProcessedOptionImpl) return default
        var extraPath = ""
        val categoryParent = processedOption.category.parentCategoryId
        if (categoryParent != null) {
            extraPath = categoryParent.split(".").last() + "."
        }
        extraPath += processedOption.getPath()
        if (default is GuiOptionEditorKeybind) {
            println("Updating keybind...")
//            UpdateKeybinds.keybinds.add(extraPath)
        }

//        EnforcedConfigValues.isBlockedFromEditing(extraPath)?.let { extraMessage ->
//            return GuiOptionEditorBlocked(default, extraMessage)
//        }
        println("hello $default")
        return default
    }
}