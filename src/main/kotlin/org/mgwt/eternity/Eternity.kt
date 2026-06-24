package org.mgwt.eternity

import com.mojang.brigadier.CommandDispatcher
import eu.midnightdust.lib.config.MidnightConfig
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStarted
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import net.minecraft.commands.CommandBuildContext
import org.mgwt.eternity.config.EternityConfig
import org.mgwt.eternity.events.AfterTranslucentRenderer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import net.minecraft.client.resources.language.I18n
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult


@Environment(EnvType.CLIENT)
object Eternity : ModInitializer {
    val LOGGER: Logger = LoggerFactory.getLogger("eternity")

    override fun onInitialize() {
        LOGGER.info("Eternity Rat Injected!")
        MidnightConfig.init("eternity", EternityConfig::class.java)
        LevelRenderEvents.AFTER_TRANSLUCENT_FEATURES.register(AfterTranslucentRenderer())
        ClientPlayConnectionEvents.JOIN.register { handler, sender, client ->
            LOGGER.info("im joined server!")
        }
        ClientLifecycleEvents.CLIENT_STARTED.register(ClientStarted {
            LOGGER.info("client started!")
        })
        ClientCommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<FabricClientCommandSource>, registryAccess: CommandBuildContext ->
            EternityCommand.register(dispatcher, registryAccess)
        }
        ClientTickEvents.START_CLIENT_TICK.register { client: Minecraft ->

        }
        ClientTickEvents.END_CLIENT_TICK.register { client: Minecraft ->
            val world = client.level ?: return@register
            val ghostBlockKeyBind = EternityConfig.ghostBlockKey
            if (ghostBlockKeyBind.isUnbound) return@register
            if (ghostBlockKeyBind.isDown) {
                val mop = client.player?.pick(5.0, 1.0F, false)
                if (mop != null && mop.type != HitResult.Type.MISS) {
                    val pos = (mop as BlockHitResult).blockPos
                    val state = world.getBlockState(pos)
                    if (state.isSolidRender) {
                        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
                    }
                }
            }
        }
    }

    fun getVersion(): String {
        return FabricLoader.getInstance()
            .getModContainer("eternity")
            .get()
            .metadata
            .version
            .friendlyString;
    }
}