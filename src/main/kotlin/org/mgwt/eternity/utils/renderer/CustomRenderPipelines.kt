package org.mgwt.eternity.utils.renderer

import com.mojang.blaze3d.pipeline.RenderPipeline
import net.minecraft.client.renderer.RenderPipelines

object CustomRenderPipelines {
    val TEST_LINES: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder(*arrayOf(RenderPipelines.LINES_SNIPPET))
            .withLocation("pipeline/lines")
//            .withDepthStencilState(DepthStencilState(CompareOp.ALWAYS_PASS, false))
            .build()
    )

    val TEST_QUADS: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder(*arrayOf(RenderPipelines.DEBUG_FILLED_SNIPPET))
            .withLocation("pipeline/debug_quads")
//            .withDepthStencilState(DepthStencilState(CompareOp.ALWAYS_PASS, false))
            .withCull(true)
            .build()
    )
}