package eu.midnightdust.lib.config;

import com.google.common.collect.Lists;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import org.jspecify.annotations.NonNull;

public class ButtonEntry extends ContainerObjectSelectionList.Entry<ButtonEntry> {
    private static final Font textRenderer = Minecraft.getInstance().font;
    public final Component text;
    public final List<AbstractWidget> buttons;
    public final EntryInfo info;
    public boolean centered = false;
    public MultiLineTextWidget title;

    public ButtonEntry(List<AbstractWidget> buttons, Component text, EntryInfo info) {
        this.buttons = buttons;
        this.text = text;
        this.info = info;
        if (info != null && info.comment != null)
            this.centered = info.comment.centered();
        int scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        if (text != null && (!text.getString().contains("spacer") || !buttons.isEmpty())) {
            title = new MultiLineTextWidget(12, 0, text.copy(), textRenderer).setCentered(centered);
            if (info != null)
                title.setTooltip(info.getTooltip(false));
            title.setMaxWidth(!buttons.isEmpty() ? buttons.get(buttons.size() > 2 ? buttons.size() - 1 : 0).getX() - 16 : scaledWidth - 24);
            if (centered) title.setX(scaledWidth / 2 - (title.getWidth() / 2));
        }
    }

    @Override
    public void extractContent(@NonNull GuiGraphicsExtractor context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
    int y = this.getY();
        buttons.forEach(b -> {
            b.setY(y);
            b.extractRenderState(context, mouseX, mouseY, tickDelta);
        });
        if (title != null) {
            title.setY(y + 5);
            title.extractRenderState(context, mouseX, mouseY, tickDelta);
        }
        if (info != null && info.entry != null && !this.buttons.isEmpty() && this.info.entry.idMode() != -1) {
            var id = Identifier.tryParse(this.info.tempValue);
            var item = this.info.entry.idMode() == 0 ? BuiltInRegistries.ITEM./*? if >= 1.21.4 {*/ getValue /*?} else {*/ /*get *//*?}*/(id) : BuiltInRegistries.BLOCK./*? if >= 1.21.4 {*/ getValue /*?} else {*/ /*get *//*?}*/(id).asItem();
            var stack = new ItemStack(Holder.direct(item, DataComponentMap.builder().set(DataComponents.ITEM_MODEL, Identifier.tryParse(this.info.tempValue)).build())) /*?} else {*/ /*item.getDefaultInstance() *//*?}*/;
            context.fakeItem(stack, this.buttons.getFirst().getX() + this.buttons.getFirst().getWidth() - 18, y + 2);
        }
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent click, boolean doubled) {
        if (this.info != null && this.info.comment != null && !this.info.comment.url().isBlank())
        {
            assert Minecraft.getInstance().gui.screen() != null;
            ConfirmLinkScreen.confirmLinkNow(Minecraft.getInstance().gui.screen(), this.info.comment.url(), true);
        }
        return super.mouseClicked(click, doubled);
    }

    public @NonNull List<? extends GuiEventListener> children() {
        return Lists.newArrayList(buttons);
    }

    public @NonNull List<? extends NarratableEntry> narratables() {
        return Lists.newArrayList(buttons);
    }
}