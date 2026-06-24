package org.mgwt.eternity.utils.renderer

import net.minecraft.client.renderer.rendertype.LayeringTransform
import net.minecraft.client.renderer.rendertype.OutputTarget
import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType

object CustomRenderType {
    val LINES: RenderType = RenderType.create(
        "no_depth_test_lines",
        RenderSetup.builder(CustomRenderPipelines.TEST_LINES)
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).createRenderSetup()
    )

    val QUADS: RenderType = RenderType.create(
        "no_depth_test_quads",
        RenderSetup.builder(CustomRenderPipelines.TEST_QUADS).sortOnUpload().createRenderSetup()
    )
}