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
import me.exz.omniocular.handler.XMLConfigEventHandler;
import me.exz.omniocular.handler.XMLConfigHandler;
import me.exz.omniocular.network.XMLConfigMessage;
import me.exz.omniocular.network.XMLConfigMessageHandler;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig(event);
        XMLConfigHandler.initConfigFiles(event);
        XMLConfigMessageHandler.network
            .registerMessage(XMLConfigMessageHandler.class, XMLConfigMessage.class, 0, Side.CLIENT);
    }

    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.waila.EntityHandler.callbackRegister");

        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.waila.TileEntityHandler.callbackRegister");
        FMLCommonHandler.instance()
            .bus()
            .register(new XMLConfigEventHandler());

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandReloadConfig());
    }

}
