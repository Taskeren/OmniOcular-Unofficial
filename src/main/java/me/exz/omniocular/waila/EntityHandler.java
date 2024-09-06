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
import me.exz.omniocular.config.Config;
import me.exz.omniocular.handler.XMLConfigHandler;

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
    private long lastTick;
    private List<String> lastTps;
    private static final List<String> EMPTY_LIST = new ArrayList<>();

    @Override
    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,
        IWailaConfigHandler config) {
        if (!Config.enableEntityInfo || Config.blackEntity.contains(
            entity.getClass()
                .getName()))
            return currenttip;

        int id = entity.getEntityId();
        long currentTick = accessor.getWorld()
            .getTotalWorldTime();
        if (id != lastEntityId || currentTick - lastTick > 10) {
            lastEntityId = id;
            lastTick = currentTick;
            NBTTagCompound n = accessor.getNBTData();
            lastTps = n != null ? JSHandler.getBody(
                XMLConfigHandler.entityPattern,
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
