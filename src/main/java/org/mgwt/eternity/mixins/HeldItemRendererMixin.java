package org.mgwt.eternity.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.mgwt.eternity.Eternity;
import org.mgwt.eternity.config.EternityConfig;
import org.mgwt.eternity.config.categories.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeldItemRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class HeldItemRendererMixin {
    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V", at = @At("HEAD"))
    private void scaleItem(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci) {
        EternityConfig config = Eternity.INSTANCE.getConfigManager().getConfig();
        UI.ItemInHandSettings settings = config.getUiCategory().getItemInHandSettings();

        if (settings.getEnableSmallItems()) {
            float size = settings.getSizeOfItem();
            float liftOffset = settings.getLiftOffset();
            matrices.scale(size, size, size);
            matrices.translate(0.0, liftOffset, 0.0);
        }
    }

    @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), index = 7, argsOnly = true)
    private float setEquipProgress(float equipProgress) {
        EternityConfig config = Eternity.INSTANCE.getConfigManager().getConfig();
        if (config.getUiCategory().getItemInHandSettings().getDisableEquip()) {
            return 0.0F;
        }
        return equipProgress;
    }

    @Inject(method = "shouldSkipHandAnimationOnSwap", at = @At("HEAD"), cancellable = true)
    private void setSkipHandAnimationOnSwap(ItemStack from, ItemStack _to, CallbackInfoReturnable<Boolean> cir) {
        EternityConfig config = Eternity.INSTANCE.getConfigManager().getConfig();
        if (config.getUiCategory().getItemInHandSettings().getDisableEquip()) {
            cir.setReturnValue(true);
        }
    }
}