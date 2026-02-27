package org.mgwt.eternity.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.text.MutableText
import net.minecraft.text.Text

object ChatUtils {
    private val prefix: Text = Text.of("§e[§6Eternity§e] ")

    fun sendMessage(component: Text) {
        val player = MinecraftClient.getInstance().player ?: return
        player.sendMessage(component, false)
    }
    fun sendMessageWatermarked(component: Text) {
        sendMessage((prefix as MutableText).append(component))
    }

    fun sendCommand(command: String) {
        val mc = MinecraftClient.getInstance()
        mc.networkHandler?.sendChatCommand(command)
    }
}