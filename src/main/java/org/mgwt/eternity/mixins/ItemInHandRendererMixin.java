package org.mgwt.eternity.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.mgwt.eternity.config.EternityConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class ItemInHandRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private void scaleItem(LivingEntity mob, ItemStack itemStack, ItemDisplayContext type, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, CallbackInfo ci) {
        if (EternityConfig.enableSmallItems) {
            float size = EternityConfig.itemSize;
            float liftOffset = EternityConfig.liftOffset;
            poseStack.scale(size, size, size);
            poseStack.translate(0.0, liftOffset, 0.0);
        }
    }

    @ModifyVariable(method = "submitArmWithItem", at = @At("HEAD"), argsOnly = true, name = "inverseArmHeight")
    private float setAttackValue(float inverseArmHeight) {
        if (EternityConfig.disableEquip) {
            return 0.0F;
        }
        return inverseArmHeight;
    }

    @Inject(method = "shouldInstantlyReplaceVisibleItem", at = @At("HEAD"), cancellable = true)
    private void shouldInstantlyReplaceVisibleItem(ItemStack currentlyVisibleItem, ItemStack expectedItem, CallbackInfoReturnable<Boolean> cir) {
        if (EternityConfig.disableEquip) {
            cir.setReturnValue(true);
        }
    }
}