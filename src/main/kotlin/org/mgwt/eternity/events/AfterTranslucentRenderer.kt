package org.mgwt.eternity.events

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.world.phys.AABB
import org.mgwt.eternity.config.EternityConfig
import org.mgwt.eternity.utils.math.Boxf
import org.mgwt.eternity.utils.renderer.CustomRenderType

class AfterTranslucentRenderer : LevelRenderEvents.AfterTranslucentFeatures {
    private val color = 0x7F00FFFF
    private val mc = Minecraft.getInstance()
    private val necronPadP4 = (-1..1).flatMap {
        x -> (-1..1).map {
            z -> BlockPos(54, 63, 114).offset(x, 0, z)
        }
    }
    val renderType = CustomRenderType.QUADS

    override fun afterTranslucentFeatures(context: LevelRenderContext) {
        if (!EternityConfig.enableFourthPhaseBBHelper) {
            return
        }
        val camera = mc.gameRenderer.mainCamera()

        val poseStack = context.poseStack()
        val collector = context.submitNodeCollector()

        poseStack.pushPose()
        for (pos in necronPadP4) {
            val boxf = Boxf(AABB(pos).inflate(0.002))
            collector.submitCustomGeometry(poseStack, renderType) { entry, buffer ->
                val offset = camera.position().reverse()
                val box: Boxf = boxf.move(offset)

                buffer.addVertex(entry, box.maxX, box.maxY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.maxY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.maxY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.maxX, box.maxY, box.maxZ).setColor(color)

                buffer.addVertex(entry, box.maxX, box.minY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.minY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.minY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.maxX, box.minY, box.minZ).setColor(color)

                buffer.addVertex(entry, box.maxX, box.maxY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.maxY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.minY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.maxX, box.minY, box.maxZ).setColor(color)

                buffer.addVertex(entry, box.maxX, box.minY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.minY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.maxY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.maxX, box.maxY, box.minZ).setColor(color)

                buffer.addVertex(entry, box.minX, box.maxY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.maxY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.minY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.minX, box.minY, box.maxZ).setColor(color)

                buffer.addVertex(entry, box.maxX, box.maxY, box.minZ).setColor(color)
                buffer.addVertex(entry, box.maxX, box.maxY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.maxX, box.minY, box.maxZ).setColor(color)
                buffer.addVertex(entry, box.maxX, box.minY, box.minZ).setColor(color)
            }
        }
        poseStack.popPose()
    }
}