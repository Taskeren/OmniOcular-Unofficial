package me.exz.omniocular.proxy;

import net.minecraftforge.client.ClientCommandHandler;

import codechicken.nei.guihook.GuiContainerManager;
import me.exz.omniocular.command.CommandLookFor;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.handler.TooltipHandler;
import me.exz.omniocular.util.LogHelper;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy extends CommonProxy {

    @Override
    public void registerClientCommand() {
        ClientCommandHandler.instance.registerCommand(new CommandLookFor());
    }

    @Override
    public void registerNEI() {
        if (ConfigHandler.enableTooltipInfo) GuiContainerManager.addTooltipHandler(new TooltipHandler());
    }

    @Override
    public void prepareConfigFiles() {
        try {
            ConfigHandler.releasePreConfigFiles();
        } catch (Exception e) {
            LogHelper.error("Can't release pre-config files");
            e.printStackTrace();
        }
        ConfigHandler.mergeConfig();
    }

}
