package me.exz.omniocular.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import me.exz.omniocular.handler.ConfigHandler;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ConfigHandler.mergeConfig();
    }
}
