package me.exz.omniocular.waila;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.exz.omniocular.config.Config;
import me.exz.omniocular.handler.XMLConfigHandler;

public class TileEntityHandler implements IWailaDataProvider {

    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        TileEntityHandler instance = new TileEntityHandler();
        registrar.registerBodyProvider(instance, Block.class);
        registrar.registerNBTProvider(instance, Block.class);
        // for (Object o : TileEntity.nameToClassMap.entrySet()) {
        // Map.Entry entry = (Map.Entry) o;
        // String key = (String) entry.getKey();
        // Boolean isBlackListed = false;
        // for (String blackItem : Reference.blackList) {
        // if (key.equals(blackItem)) {
        // isBlackListed = true;
        // break;
        // }
        // }
        // if (!isBlackListed) {
        // registrar.registerBodyProvider(instance, (Class) entry.getValue());
        // registrar.registerNBTProvider(instance, (Class) entry.getValue());
        // }
        // }
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return currenttip;
    }

    private int lastItemStackHash;
    private List<String> lastTps;
    private long lastTick;
    private static final List<String> EMPTY_LIST = new ArrayList<>();

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        if (!Config.enableTileEntityInfo) return currenttip;

        Item item = itemStack.getItem();
        int hashCode = item == null ? 0 : item.hashCode();
        long currentTick = accessor.getWorld()
            .getTotalWorldTime();

        if (hashCode != lastItemStackHash || currentTick - lastTick > 20) {
            lastTick = currentTick;
            lastItemStackHash = hashCode;
            NBTTagCompound n = accessor.getNBTData();
            lastTps = n != null
                ? JSHandler.getBody(XMLConfigHandler.tileEntityPattern, n, n.getString("id"), accessor.getPlayer())
                : EMPTY_LIST;
        }

        currenttip.addAll(lastTps);
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
        int y, int z) {
        if (te != null) te.writeToNBT(tag);
        return tag;
    }
}
