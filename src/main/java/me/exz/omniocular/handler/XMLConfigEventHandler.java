package me.exz.omniocular.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import me.exz.omniocular.network.XMLConfigMessageHandler;

public class XMLConfigEventHandler {

    @SubscribeEvent
    public void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        XMLConfigMessageHandler
            .sendConfigString(XMLConfigHandler.mergedConfig, (net.minecraft.entity.player.EntityPlayerMP) event.player);

    }
}
