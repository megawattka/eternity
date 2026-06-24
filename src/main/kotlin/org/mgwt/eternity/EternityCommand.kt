package org.mgwt.eternity

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import eu.midnightdust.lib.config.MidnightConfig
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.tabs.Tab
import net.minecraft.commands.CommandBuildContext
import org.mgwt.eternity.utils.TabListUtils

//import org.mgwt.eternity.utils.SkyblockUtils


object EternityCommand {
    fun register(
        dispatcher: CommandDispatcher<FabricClientCommandSource>,
        registryAccess: CommandBuildContext
    ) {
        dispatcher.register(
            ClientCommands.literal("eternity")
                .executes { context: CommandContext<FabricClientCommandSource> ->
                    val mc = Minecraft.getInstance()
                    mc.execute {
                        mc.gui.setScreen(MidnightConfig.getScreen(null, "eternity"))
                    }
                    Command.SINGLE_SUCCESS
                })
    }
}