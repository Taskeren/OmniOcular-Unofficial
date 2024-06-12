package me.exz.omniocular.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import me.exz.omniocular.config.Config;
import me.exz.omniocular.handler.XMLConfigHandler;

public class FMPHandler {

    @SuppressWarnings("UnusedDeclaration")
    public static void getWailaBody(List<String> currenttip, NBTTagCompound nbt, EntityPlayer player) {
        if (!Config.enableFMPInfo) return;

        if (nbt != null) {
            currenttip.addAll(JSHandler.getBody(XMLConfigHandler.tileEntityPattern, nbt, nbt.getString("id"), player));
        }
    }
}
