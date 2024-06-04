package me.exz.omniocular.handler;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import me.exz.omniocular.config.Config;
import me.exz.omniocular.network.XMLConfigMessageHandler;

public class XMLConfigEventHandler {

    @SubscribeEvent
    public void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (MinecraftServer.getServer()
            .isDedicatedServer() && Config.sendToClientXML)
            XMLConfigMessageHandler.sendConfigString(
                XMLConfigHandler.mergedConfig,
                (net.minecraft.entity.player.EntityPlayerMP) event.player);

    }
}
