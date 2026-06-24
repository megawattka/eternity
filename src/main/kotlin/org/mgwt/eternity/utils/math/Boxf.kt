package org.mgwt.eternity.utils.math

import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

//this((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1));
class Boxf(box: AABB) {
    var minX: Float = box.minX.toFloat()
    var minY: Float = box.minY.toFloat()
    var minZ: Float = box.minZ.toFloat()
    var maxX: Float = box.maxX.toFloat()
    var maxY: Float = box.maxY.toFloat()
    var maxZ: Float = box.maxZ.toFloat()

    fun move(offset: Vec3): Boxf {
        return Boxf(
            AABB(
                this.minX + offset.x,
                this.minY + offset.y,
                this.minZ + offset.z,
                this.maxX + offset.x,
                this.maxY + offset.y,
                this.maxZ + offset.z
            )
        )
    }
}