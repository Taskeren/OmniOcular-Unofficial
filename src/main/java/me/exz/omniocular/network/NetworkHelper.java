package me.exz.omniocular.network;

import net.minecraft.entity.player.EntityPlayerMP;

import me.exz.omniocular.handler.XMLConfigHandler;
import me.exz.omniocular.util.LogHelper;

public class NetworkHelper {

    private static void sendString(String string, EntityPlayerMP player) {
        ConfigMessageHandler.network.sendTo(new ConfigMessage(string), player);
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

    static void recvConfigString(String string) {
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
