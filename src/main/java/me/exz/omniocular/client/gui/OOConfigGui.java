package me.exz.omniocular.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.config.GuiConfig;
import me.exz.omniocular.config.Config;
import me.exz.omniocular.reference.Reference;

public class OOConfigGui extends GuiConfig {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public OOConfigGui(GuiScreen guiScreen) {
        super(
            guiScreen,
            new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
            Reference.MOD_ID,
            false,
            false,
            GuiConfig.getAbridgedConfigPath(Config.config.toString()));
    }
}
