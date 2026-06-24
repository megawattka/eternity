package org.mgwt.eternity.utils

import net.minecraft.client.Minecraft

object TabListUtils {
    fun getIslandType(): String? {
        val connection = Minecraft.getInstance().connection
        val players = connection?.onlinePlayers ?: return null
        for (player in players) {
            val name = player.tabListDisplayName?.string;
            if (name?.contains("Area:") == true) {
                return name.replace("Area: ", "").trim()
            }
        }
        return null
    }
}