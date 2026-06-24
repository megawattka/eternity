package org.mgwt.eternity.compat

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import eu.midnightdust.lib.config.MidnightConfig
import net.minecraft.client.gui.screens.Screen
import org.mgwt.eternity.Eternity


class ModMenuImpl : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent: Screen? -> MidnightConfig.getScreen(parent, "eternity") }
    }
}