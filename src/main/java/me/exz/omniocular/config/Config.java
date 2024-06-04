package me.exz.omniocular.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.exz.omniocular.reference.Reference;

public class Config {

    public static Configuration config;
    public static boolean enableEntityInfo = true;
    public static boolean enableFMPInfo = true;
    public static boolean enableTileEntityInfo = true;
    public static boolean enableTooltipInfo = true;
    public static boolean sendToClientXML = true;

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfig();
        }
    }

    public static void initConfig(FMLPreInitializationEvent event) {

        FMLCommonHandler.instance()
            .bus()
            .register(new Config());

        config = new Configuration(new File(event.getModConfigurationDirectory(), Reference.OLD_MOD_ID + ".cfg"));
        loadConfig();
    }

    private static void loadConfig() {

        enableEntityInfo = config.getBoolean(
            "enableEntityInfo",
            Configuration.CATEGORY_GENERAL,
            enableEntityInfo,
            "Handle entity information.");
        enableFMPInfo = config
            .getBoolean("enableFMPInfo", Configuration.CATEGORY_GENERAL, enableFMPInfo, "Handle FMP information.");
        enableTileEntityInfo = config.getBoolean(
            "enableTileEntityInfo",
            Configuration.CATEGORY_GENERAL,
            enableTileEntityInfo,
            "Handle TileEntity information.");
        enableTooltipInfo = config.getBoolean(
            "enableTooltipInfo",
            Configuration.CATEGORY_GENERAL,
            enableTooltipInfo,
            "Handle Tooltip information.");
        sendToClientXML = config.getBoolean(
            "sendToClientXML",
            Configuration.CATEGORY_GENERAL,
            sendToClientXML,
            "Use the server-side XML configuration");

        if (config.hasChanged()) {
            config.save();
        }
    }
}
