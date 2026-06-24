package eu.midnightdust.core;

import eu.midnightdust.core.config.MidnightLibConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.UIManager;
import net.minecraft.util.Util;
import java.util.ArrayList;

import java.util.List;

import net.fabricmc.api.ClientModInitializer;

public class MidnightLib implements ClientModInitializer {
    public static List<String> hiddenMods = new ArrayList<>();
    public static final String MOD_ID = "midnightlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitializeClient() {
        try {
            if (Util.getPlatform() != Util.OS.OSX) {
                System.setProperty("java.awt.headless", "false");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception | Error e) { LOGGER.error("Error setting system look and feel", e); }
        MidnightLibConfig.init(MOD_ID, MidnightLibConfig.class);
    }
}
