package org.mgwt.eternity.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.blaze3d.platform.InputConstants;
import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.config.MidnightConfigListWidget;
import eu.midnightdust.lib.config.MidnightConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class EternityConfig extends MidnightConfig {
    public static final String ITEM_IN_HAND_SETTINGS = "itemInHandSettings";
    public static final String DUNGEONS = "dungeons";

    @Entry(category = ITEM_IN_HAND_SETTINGS)
    public static boolean enableSmallItems = false;

    @Condition(requiredOption = "eternity:enableSmallItems", visibleButLocked = true)
    @Entry(category = ITEM_IN_HAND_SETTINGS, isSlider = true, min = 0.1, max = 1)
    public static float itemSize = 1.0F;

    @Condition(requiredOption = "eternity:enableSmallItems", visibleButLocked = true)
    @Entry(category = ITEM_IN_HAND_SETTINGS, isSlider = true, min = 0.1, max = 1)
    public static float liftOffset = 0.1F;

    @Entry(category = ITEM_IN_HAND_SETTINGS)
    public static boolean disableEquip = false;

    @Entry(category = DUNGEONS)
    public static boolean enableFourthPhaseBBHelper = true;

    @Entry(category = DUNGEONS)
    public static KeyMapping ghostBlockKey = new KeyMapping(
        "eternity.midnightconfig.ghostBlockKey",
        GLFW.GLFW_KEY_V,
        KeyMapping.Category.MISC
    );

    @Override
    public void onTabInit(String tabName, MidnightConfigListWidget list, MidnightConfigScreen screen) {
        if (Objects.equals(tabName, DUNGEONS)) {
            MidnightLibExtras.KeybindButton.add(ghostBlockKey, list, screen);
        }
    }

}