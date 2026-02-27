package org.mgwt.eternity

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import io.github.notenoughupdates.moulconfig.gui.GuiContext
import io.github.notenoughupdates.moulconfig.gui.GuiElementComponent
import io.github.notenoughupdates.moulconfig.platform.MoulConfigScreenComponent
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.text.Text
import org.mgwt.eternity.utils.SkyblockUtils


object EternityCommand {
    fun register(
        dispatcher: CommandDispatcher<FabricClientCommandSource?>,
        registryAccess: CommandRegistryAccess?
    ) {
        dispatcher.register(
            ClientCommandManager.literal("eternity")
                .executes { context: CommandContext<FabricClientCommandSource> ->
                    val editor = Eternity.configManager.getConfigEditor()
                    val element = MoulConfigScreenComponent(
                        Text.empty(),
                        GuiContext(GuiElementComponent(editor)),
                        null
                    )
                    Eternity.nextScreen = element
                    0
                })
    }
}