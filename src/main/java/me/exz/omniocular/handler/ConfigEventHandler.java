package me.exz.omniocular.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import me.exz.omniocular.network.ConfigMessageHandler;

public class ConfigEventHandler {

    @SubscribeEvent
    public void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        ConfigMessageHandler
            .sendConfigString(XMLConfigHandler.mergedConfig, (net.minecraft.entity.player.EntityPlayerMP) event.player);

    }
}
