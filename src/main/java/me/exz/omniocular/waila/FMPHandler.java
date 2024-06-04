package me.exz.omniocular.waila;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import mcp.mobius.waila.api.IWailaFMPAccessor;
import me.exz.omniocular.config.Config;
import me.exz.omniocular.handler.XMLConfigHandler;

public class FMPHandler {

    @SuppressWarnings("UnusedDeclaration")
    public static List<String> getWailaBody(List<String> currenttip, IWailaFMPAccessor accessor) {
        if (!Config.enableFMPInfo) return currenttip;

        NBTTagCompound n = accessor.getNBTData();
        // accessor.getTileEntity().writeToNBT(n);
        if (n != null) {
            currenttip.addAll(
                JSHandler.getBody(XMLConfigHandler.tileEntityPattern, n, n.getString("id"), accessor.getPlayer()));
        }
        return currenttip;
    }
}
