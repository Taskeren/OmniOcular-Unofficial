package me.exz.omniocular.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import me.exz.omniocular.command.CommandReloadConfig;
import me.exz.omniocular.config.Config;
import me.exz.omniocular.handler.ConfigEventHandler;
import me.exz.omniocular.handler.XMLConfigHandler;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig(event);
        XMLConfigHandler.initConfigFiles(event);

        // JSHandler.initEngine();
        ConfigMessageHandler.network.registerMessage(ConfigMessageHandler.class, ConfigMessage.class, 0, Side.CLIENT);
    }

    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.waila.EntityHandler.callbackRegister");

        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.waila.TileEntityHandler.callbackRegister");
        FMLCommonHandler.instance()
            .bus()
            .register(new ConfigEventHandler());

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandReloadConfig());
    }

}
