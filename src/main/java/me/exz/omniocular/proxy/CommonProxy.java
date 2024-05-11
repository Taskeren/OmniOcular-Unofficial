package me.exz.omniocular.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import me.exz.omniocular.command.CommandReloadConfig;
import me.exz.omniocular.handler.ConfigEventHandler;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;

public abstract class CommonProxy implements IProxy {

    @Override
    public void registerServerCommand(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandReloadConfig());
    }

    @Override
    public void registerEventHandler() {
        FMLCommonHandler.instance()
            .bus()
            .register(new ConfigEventHandler());
    }

    @Override
    public void registerWaila() {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.handler.EntityHandler.callbackRegister");

        FMLInterModComms
            .sendMessage("Waila", "register", "me.exz.omniocular.handler.TileEntityHandler.callbackRegister");
    }

    @Override
    public void registerNetwork() {
        ConfigMessageHandler.network.registerMessage(ConfigMessageHandler.class, ConfigMessage.class, 0, Side.CLIENT);

    }

    @Override
    public void initConfig(FMLPreInitializationEvent event) {
        ConfigHandler.initConfigFiles(event);
        // JSHandler.initEngine();
    }
}
