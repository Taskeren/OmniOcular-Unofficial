package me.exz.omniocular.waila;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import mcp.mobius.waila.api.IWailaFMPAccessor;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.handler.JSHandler;

public class FMPHandler {

    @SuppressWarnings("UnusedDeclaration")
    public static List<String> getWailaBody(List<String> currenttip, IWailaFMPAccessor accessor) {
        NBTTagCompound n = accessor.getNBTData();
        // accessor.getTileEntity().writeToNBT(n);
        if (n != null) {
            currenttip
                .addAll(JSHandler.getBody(ConfigHandler.tileEntityPattern, n, n.getString("id"), accessor.getPlayer()));
        }
        return currenttip;
    }
}
