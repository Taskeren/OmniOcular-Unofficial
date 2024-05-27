package me.exz.omniocular.proxy;

import net.minecraftforge.client.ClientCommandHandler;

import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import me.exz.omniocular.client.command.CommandLookFor;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.util.LogHelper;
import me.exz.omniocular.waila.TooltipHandler;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientCommandHandler.instance.registerCommand(new CommandLookFor());
        try {
            ConfigHandler.releasePreConfigFiles();
        } catch (Exception e) {
            LogHelper.error("Can't release pre-config files");
            e.printStackTrace();
        }
        ConfigHandler.mergeConfig();

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        GuiContainerManager.addTooltipHandler(new TooltipHandler());
    }

}
