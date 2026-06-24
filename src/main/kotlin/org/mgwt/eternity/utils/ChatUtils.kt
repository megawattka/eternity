package org.mgwt.eternity.utils

import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object ChatUtils {
    private val prefix: Component = Component.literal("§e[§6Eternity§e] ")

    fun sendMessage(component: Component) {
        val player = Minecraft.getInstance().player ?: return
        player.sendSystemMessage(component)
    }
    fun sendMessageWatermarked(component: Component) {
        sendMessage((prefix as MutableComponent).append(component))
    }

    fun sendCommand(command: String) {
        val mc = Minecraft.getInstance()
        mc.connection?.sendCommand(command)
    }
}