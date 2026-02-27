package org.mgwt.eternity.utils

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext
import net.minecraft.client.render.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import java.awt.Color


object Render3D {
    fun renderDebugFilledBoxArea(
        context: WorldRenderContext,
        blocks: HashSet<BlockPos>,
        color: Color
    ) {
        val matrices = context.matrices().peek().positionMatrix
        val consumers = context.consumers()
        val consumer: VertexConsumer = consumers.getBuffer(RenderLayer.getDebugSectionQuads())
        val camera = context.gameRenderer().camera
        blocks.forEach { block ->
            val box = Box(block)
            val shifted = box.offset(
                -camera.pos.x,
                -camera.pos.y,
                -camera.pos.z
            )
            val minX = shifted.minX.toFloat()
            val minY = shifted.minY.toFloat()
            val minZ = shifted.minZ.toFloat()
            val maxX = shifted.maxX.toFloat()
            val maxY = shifted.maxY.toFloat()
            val maxZ = shifted.maxZ.toFloat()

            val red = color.red.toFloat() / 255f
            val green = color.green.toFloat() / 255f
            val blue = color.blue.toFloat() / 255f
            val alpha = color.alpha.toFloat() / 255f


            // TODO: make sideContext top and bottom
            VertexRendering.drawSide(matrices, consumer, Direction.DOWN, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
            VertexRendering.drawSide(matrices, consumer, Direction.UP, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);

            if (!blocks.contains(block.offset(Direction.NORTH))) {
                VertexRendering.drawSide(matrices, consumer, Direction.NORTH, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
            }
            if (!blocks.contains(block.offset(Direction.SOUTH))) {
                VertexRendering.drawSide(matrices, consumer, Direction.SOUTH, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
            }
            if (!blocks.contains(block.offset(Direction.WEST))) {
                VertexRendering.drawSide(matrices, consumer, Direction.WEST, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
            }
            if (!blocks.contains(block.offset(Direction.EAST))) {
                VertexRendering.drawSide(matrices, consumer, Direction.EAST, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha)
            }
        }
    }
}
