package me.exz.omniocular.waila;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.handler.JSHandler;

public class EntityHandler implements IWailaEntityProvider {

    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        EntityHandler instance = new EntityHandler();
        // registrar.registerSyncedNBTKey("*", Entity.class);
        registrar.registerBodyProvider(instance, Entity.class);
        registrar.registerNBTProvider(instance, Entity.class);

    }

    @Override
    public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,
        IWailaConfigHandler config) {
        return currenttip;
    }

    private int lastEntityId;
    private List<String> lastTps;
    private static final List<String> EMPTY_LIST = new ArrayList<>();

    @Override
    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,
        IWailaConfigHandler config) {
        if (!ConfigHandler.enableEntityInfo) return currenttip;

        int id = entity.getEntityId();
        if (id != lastEntityId || (entity.worldObj.getTotalWorldTime() % 10 == 0)) {
            lastEntityId = id;
            NBTTagCompound n = accessor.getNBTData();
            lastTps = n != null ? JSHandler.getBody(
                ConfigHandler.entityPattern,
                n,
                EntityList.getEntityString(accessor.getEntity()),
                accessor.getPlayer()) : EMPTY_LIST;
        }

        currenttip.addAll(lastTps);
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,
        IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
        if (ent != null) ent.writeToNBT(tag);
        return tag;

    }
}
