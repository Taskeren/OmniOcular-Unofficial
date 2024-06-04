package me.exz.omniocular.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import me.exz.omniocular.handler.XMLConfigHandler;
import me.exz.omniocular.network.NetworkHelper;
import me.exz.omniocular.util.LogHelper;

public class CommandReloadConfig extends CommandBase {

    @Override
    public String getCommandName() {
        return "oor";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return MinecraftServer.getServer()
            .isSinglePlayer() || super.canCommandSenderUseCommand(sender);
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/oor";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] array) {
        XMLConfigHandler.mergeConfig();
        List<EntityPlayerMP> playerList = MinecraftServer.getServer()
            .getConfigurationManager().playerEntityList;
        for (EntityPlayerMP player : playerList) {
            // ConfigMessageHandler.network.sendTo(new ConfigMessage(ConfigHandler.mergedConfig), (EntityPlayerMP)
            // player);
            NetworkHelper.sendConfigString(XMLConfigHandler.mergedConfig, player);
        }
        LogHelper.info(sender.getCommandSenderName() + " commit a config reload.");
    }
}
