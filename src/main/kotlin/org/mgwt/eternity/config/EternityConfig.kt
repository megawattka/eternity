package org.mgwt.eternity.config

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.Config
import io.github.notenoughupdates.moulconfig.annotations.Category
import org.mgwt.eternity.Eternity
import org.mgwt.eternity.config.categories.Dev
import org.mgwt.eternity.config.categories.Dungeons
import org.mgwt.eternity.config.categories.UI

class EternityConfig : Config() {
    val title: String
        get() = "Example Mod " + Eternity.getVersion() + " by §channibal2§r, config by §5Moulberry §rand §5nea89"

    override fun saveNow() {
        Eternity.configManager.save()
    }

    @Expose
    @Category(name = "Item in hand", desc = "Changes look of item in main hand.")
    var uiCategory: UI = UI()

    @Expose
    @Category(name = "Dungeons", desc = "Dungeon utilities")
    var dungeons: Dungeons = Dungeons()

    @Expose
    @Category(name = "Dev", desc = "Dev features")
    var dev: Dev = Dev()
}