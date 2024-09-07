package me.exz.omniocular.config;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
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

    private static String[] blackTileEntityNames = new String[0];
    public static String[] scriptClassName = new String[0];
    public static Set<Integer> blackTileEntity = new HashSet<>();

    public static Set<String> blackEntity = new HashSet<>();

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

        config = new Configuration(new File(event.getModConfigurationDirectory(), Reference.MOD_NAME + ".cfg"));
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

        blackTileEntityNames = config.getStringList(
            "blackTileEntityNames",
            Configuration.CATEGORY_GENERAL,
            blackTileEntityNames,
            "Black TileEntity Names list. as: minecraft:furnace@0");

        String[] blackEntityNames = config.getStringList(
            "blackEntityNames",
            Configuration.CATEGORY_GENERAL,
            new String[] { "net.minecraft.entity.player.EntityPlayer" },
            "Black Entity Names list. as: net.minecraft.entity.player.EntityPlayer");

        scriptClassName = config.getStringList(
            "scriptClassName",
            Configuration.CATEGORY_GENERAL,
            new String[] {},
            "The class name of the script written in java. as: me.exz.omniocular.scripts.GTNHScript");

        blackEntity.addAll(Arrays.asList(blackEntityNames));

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void preprocess() {
        for (String blackTileEntityName : blackTileEntityNames) {
            String[] ss = blackTileEntityName.split("@", 2);
            Block block = Block.getBlockFromName(ss[0]);
            if (block != null) {
                if (ss.length == 2) {
                    try {
                        blackTileEntity.add(Block.getIdFromBlock(block) << 16 | Integer.parseInt(ss[1]));
                    } catch (NumberFormatException ignored) {}
                } else {
                    blackTileEntity.add(Block.getIdFromBlock(block) << 16);
                }

            }
        }
    }
}
