package org.mgwt.eternity

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStarted
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Blocks
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.InputUtil
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import org.mgwt.eternity.config.ConfigManager
import org.mgwt.eternity.utils.SkyblockUtils
import org.mgwt.eternity.utils.Render3D
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color


@Environment(EnvType.CLIENT)
object Eternity : ModInitializer {
    val logger: Logger = LoggerFactory.getLogger("eternity")
    val configManager = ConfigManager()
    var nextScreen: Screen? = null

    override fun onInitialize() {
        logger.info("Eternity Rat Injected!")
        val platform = BlockPos(54, 63, 114)  // p4 necron pad
        val cyan = Color(0f, 1f, 1f, 0.55f)
        val p4BBnecron: HashSet<BlockPos> = hashSetOf()
        for (x in -1..1) {
            for (z in -1..1) {
                val position = platform.add(x, 0, z)
                p4BBnecron.add(position)
            }
        }
        WorldRenderEvents.END_MAIN.register { context ->
            if (configManager.getConfig().dungeons.enableP4bbHelper) {
                Render3D.renderDebugFilledBoxArea(context, p4BBnecron, cyan)
            }
        }
        ClientPlayConnectionEvents.JOIN.register { handler, sender, client ->
            SkyblockUtils.isOnSkyblock()
            println("im joined server!")
        }
        ClientLifecycleEvents.CLIENT_STARTED.register(ClientStarted {
            logger.info("CLIENT STARTED")
        })
        ClientCommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<FabricClientCommandSource?>, registryAccess: CommandRegistryAccess? ->
            EternityCommand.register(dispatcher, registryAccess)
        }
        ClientTickEvents.START_CLIENT_TICK.register { client: MinecraftClient ->
            val player = client.player
            if (nextScreen != null) {
                if (player?.currentScreenHandler != null) {
                    player.closeHandledScreen()
                }
                client.setScreen(nextScreen)
                nextScreen = null
            }
        }
        ClientTickEvents.END_CLIENT_TICK.register { client: MinecraftClient ->
            val world = client.world ?: return@register
            val ghostBlockKeyBind = configManager.getConfig().dungeons.ghostBlockKeybind
            if (InputUtil.isKeyPressed(client.window, ghostBlockKeyBind)) {
                val mop = client.player?.raycast(5.0, 1.0F, false)
                if (mop != null && mop.type != HitResult.Type.MISS) {
                    val pos = (mop as BlockHitResult).blockPos
                    val state = world.getBlockState(pos)
                    if (state != null && state.isFullCube(world, pos)) {
                        world.setBlockState(pos, Blocks.AIR.defaultState);
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