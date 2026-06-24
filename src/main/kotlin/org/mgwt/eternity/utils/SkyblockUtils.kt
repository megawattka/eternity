package org.mgwt.eternity.utils
//
//import net.minecraft.client.MinecraftClient
//import net.minecraft.scoreboard.ScoreboardObjective
//
//
//fun Boolean?.orFalse(): Boolean = this ?: false
//
//object SkyblockUtils {
//    private fun getSkyblockScoreboard(): ScoreboardObjective? {
//        val scoreboard = MinecraftClient.getInstance().world?.scoreboard
//        return scoreboard?.objectives?.find { it.name == "SBScoreboard" }
//    }
//
//    fun isOnSkyblock(): Boolean {
//        val objective = getSkyblockScoreboard()
//        return objective?.displayName?.string?.startsWith("SKYBLOCK").orFalse()
//    }
//}