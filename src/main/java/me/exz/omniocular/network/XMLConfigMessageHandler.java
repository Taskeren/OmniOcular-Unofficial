package me.exz.omniocular.network;

import net.minecraft.entity.player.EntityPlayerMP;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import me.exz.omniocular.handler.XMLConfigHandler;
import me.exz.omniocular.reference.Reference;
import me.exz.omniocular.util.LogHelper;

public class XMLConfigMessageHandler implements IMessageHandler<XMLConfigMessage, IMessage> {

    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    @Override
    public IMessage onMessage(XMLConfigMessage message, MessageContext ctx) {
        // LogHelper.info("Config Received: "+ message.text);
        recvConfigString(message.text);
        return null;
    }

    private static void sendString(String string, EntityPlayerMP player) {
        XMLConfigMessageHandler.network.sendTo(new XMLConfigMessage(string), player);
    }

    public static void sendConfigString(String string, EntityPlayerMP player) {
        sendString("__START__", player);
        int size = 10240;
        while (string.length() > size) {
            sendString(string.substring(0, size), player);
            string = string.substring(size);
        }
        if (!string.isEmpty()) {
            sendString(string, player);
        }
        sendString("__END__", player);
    }

    private static void recvConfigString(String string) {
        switch (string) {
            case "__START__" -> XMLConfigHandler.mergedConfig = "";
            case "__END__" -> {
                LogHelper.info("received config: " + XMLConfigHandler.mergedConfig);
                XMLConfigHandler.parseConfigFiles();
            }
            default -> XMLConfigHandler.mergedConfig += string;
        }
    }
}
